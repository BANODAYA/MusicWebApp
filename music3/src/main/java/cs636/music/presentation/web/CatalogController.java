package cs636.music.presentation.web;

// TODO: pa2: add methods here to service URLs listed below
// which involve forwarding to JSPs listed below as VIEWs or in some cases
// to URLs

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import cs636.music.domain.Cart;
import cs636.music.domain.CartItem;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.presentation.client.PresentationUtils;
import cs636.music.service.ServiceException;
import cs636.music.service.UserService;
import cs636.music.service.data.CartItemData;
import cs636.music.service.data.UserData;

@Controller
public class CatalogController {
	@Autowired
	private UserService userService;

	// String constants for URL's : please use these!
	private static final String WELCOME_URL = "/welcome.html";
	private static final String WELCOME_VIEW = "/welcome";
	private static final String USER_WELCOME_URL = "/userWelcome.html";
	private static final String CATALOG_URL = "/catalog.html";
	private static final String CATALOG_VIEW = "/WEB-INF/jsp/catalog";
	private static final String CART_URL = "/cart.html";
	private static final String CART_VIEW = "/WEB-INF/jsp/cart";
	private static final String PRODUCT_URL = "/product.html";
	private static final String PRODUCT_VIEW = "/WEB-INF/jsp/product";
	private static final String LISTEN_URL = "/listen.html";
	private static final String SOUND_VIEW = "/WEB-INF/jsp/sound";
	private static final String DOWNLOAD_URL = "/download.html"; // download.html didn't work
	private static final String REGISTER_FORM_VIEW = "/WEB-INF/jsp/registerForm";

	@RequestMapping(WELCOME_URL)
	public String handleWelcome(HttpServletRequest request ,HttpServletResponse response) {
		
		HttpSession session = request.getSession();	
		
		UserBean userBean = (UserBean) session.getAttribute("user");//IF USER DOES NOT HAVE BEAN.
		if (userBean == null) {
			userBean = new UserBean();
			session.setAttribute("user", userBean);
		}
		return WELCOME_VIEW;
	}
	
	@RequestMapping(CATALOG_URL)
	public String catalogDisplay(HttpServletRequest request, HttpServletResponse response) 
	     throws IOException, ServletException {
		
		//Creating ArrayList of products and displaying them.
		ArrayList<Product> products ;
		
		try {
			products = new ArrayList<Product>(userService.getProductList());
		}catch(ServiceException e) {
			throw new ServletException(e);
			}
		request.setAttribute("products", products);//requesting servlet to set attributes.
		return CATALOG_VIEW;
		
	}
	@RequestMapping(CART_URL)
	public String cartDisplay(HttpServletRequest request, HttpServletResponse response) 
		     throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		Cart userCart = userBean.getCart();
		UserData user = userBean.getUser();
		Product products = null;
        String addNewItem = request.getParameter("addNewItem") ;
		
        if(addNewItem != null) { //if addNewItem is not null we will be displaying our products
			products = userBean.getProduct();
			System.out.println(products.getCode());//getting code of the product inorder to verify.
		}
        
        if(userCart == null) {//if userCart is null then add a cart for user
        	System.out.println("Cart for user Creating....");
        	userCart = userService.createCart();
        }
        //A bug : checking whether are they any new product to add
        if((addNewItem != null) && (products != null)) {
        	System.out.println("Products adding to cart");
        	userService.addItemtoCart(products, userCart, 1);
        }else{
        
        try {
        	userService.getCartInfo(userCart);
        	for(CartItem items : userCart.getItems()) {
        		System.out.println(items.getProductId());
        	}
        }catch (ServiceException e) {
			System.out.println(e);
		}
		}
	
	userBean.setCart(userCart);//setting cart for user
	session.setAttribute("user", userBean);
	request.setAttribute("cart", userCart);//requesting servlet to set attributes.
	
	//if user is not null we return cart view otherwise register form view.
      if(user != null) {
    	  return CART_VIEW;
      }else {
    	  return REGISTER_FORM_VIEW;
      }
        
	}
	
	@RequestMapping(PRODUCT_URL)
	public String productDisplay(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		Product products = null; ;
		String productCode = (String) request.getParameter("productCode");
        String Url = userBean.getUser() == null ? "listen.html" : "cart.html?addItem=true";
        //checking if product code is null or not!!!
        //if not get products and set it to userBean
        if ((productCode != null) && (!productCode.equals(""))) {
			try {
				 products = userService.getProduct(productCode);
				userBean.setProduct(products);
			} catch (ServiceException e) {
				
				throw new ServletException(e);
			}
		}
		request.setAttribute("listenToNextUrl",Url);//requesting servlet to set attributes.
		request.setAttribute("products", products);//requesting servlet to set attributes.
		session.setAttribute("user", userBean);
		return PRODUCT_VIEW;
	}
	
	
	@RequestMapping(LISTEN_URL)
	public String listenSong(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		Product products = userBean.getProduct();
		UserData user = userBean.getUser();
		request.setAttribute("products", products);//requesting servlet to set attributes.
		request.setAttribute("tracks", products.getTracks());//requesting servlet to set attributes.
		//if user is not null we return sound view otherwise register form view.
		if(user != null) {
	    	  return SOUND_VIEW;
	      }else {
	    	  return REGISTER_FORM_VIEW;
	      }
	}
	@RequestMapping(DOWNLOAD_URL)
	private String productDownload(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("Starting to download.......");
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		UserData user = userBean.getUser();
		Product products = userBean.getProduct();
		Integer trackNo = Integer.parseInt((String) request.getParameter("trackNum"));//keeping track for trackNum
		Track tr = null;
		try {
			 tr = products.findTrackbyID(trackNo);
			userService.addDownload(user.getId(), tr);
		} catch (ServiceException e) {
		
			throw new ServletException(e);
		}
	    String downloadURL = "soundCode/" + products.getCode() + "/" + tr.getSampleFilename();
		return downloadURL; 
	}

	

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


