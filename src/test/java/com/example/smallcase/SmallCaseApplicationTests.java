package com.example.smallcase;

import com.example.smallcase.model.*;
import com.example.smallcase.repository.*;
import com.example.smallcase.service.BasketService;
import com.example.smallcase.service.CartService;
import com.example.smallcase.service.StocksService;
import com.example.smallcase.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SmallCaseApplicationTests {
	@MockBean
	Authentication authentication;
	@MockBean
	SecurityContext securityContext;
	@MockBean
	ApplicationUserRepository applicationUserRepository;
	@MockBean
	BasketsRepository basketsRepository;
	@MockBean
	StocksRepository stocksRepository;
	@MockBean
	StocksMappingRepository stocksMappingRepository;
	@MockBean
	CartRepository cartRepository;
	@MockBean
	CartMappingRepository cartMappingRepository;
	@Autowired
	private UserService userService;
	@Autowired
	BasketService basketService;
	@Autowired
	StocksService stocksService;
	@Autowired
	CartService cartService;
	ApplicationUser user = new ApplicationUser();

	Set<StocksMapping> stocksMappingSet=new HashSet<>();
	Baskets basket = new Baskets(1L,"basket1","basket1 desc",stocksMappingSet,null);
	Stocks stock = new Stocks(1L,"stock1",1200F,stocksMappingSet);
	StocksMapping stocksMapping = new StocksMapping(1L,stock,basket,4);

	@BeforeEach
	public  void setUp(){
		user.setUserId(1L);
		user.setFirstName("ram");
		user.setLastName("kumar");
		user.setUserName("ram1");
		user.setPassword("789");
	}

	@Test
	void getUserTest(){
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(authentication.getName()).thenReturn(user.getUserName());
		when(applicationUserRepository.findByUserName(user.getUserName())).thenReturn(user);
		List<ApplicationUser> list = new ArrayList<>();
		list.add(user);
		when(applicationUserRepository.findAll()).thenReturn(list);
		//userService.getUser() - asking for auth
		assertEquals(user.getUserId(),userService.getUser());
	}

	@Test
	void registerUserTest(){
		//when(applicationUserRepository.save(user)).thenReturn(user);
		assertEquals(HttpStatus.CREATED,userService.registerUser(user).getStatusCode());
	}

	@Test
	void registerUserWithLessInfoTest(){
		ApplicationUser applicationUser1 = new ApplicationUser();
		applicationUser1.setFirstName("Rohit");
		applicationUser1.setLastName("Sharma");//userName
		applicationUser1.setPassword("123");
		assertEquals(HttpStatus.BAD_REQUEST,userService.registerUser(applicationUser1).getStatusCode());
	}

	@Test
	void doNotRegisterExistingUser(){
		when(applicationUserRepository.existsByUserName(user.getUserName())).thenReturn(true);
		assertEquals(HttpStatus.BAD_REQUEST,userService.registerUser(user).getStatusCode());
	}
	@Test
	void addBasketTest(){
		stocksMapping.setQty(2);
		stocksMapping.setStocks(stock);
		stocksMappingSet.add(stocksMapping);
		List<Long> Ids = basket.getStocksMappings().stream().map(x -> x.getStocks().getStockId())
				.collect(Collectors.toList());
		long count = Ids.stream().count();
		when(stocksRepository.checkStocks(Ids)).thenReturn((int)count);
		assertEquals(HttpStatus.CREATED,basketService.addBaskets(basket).getStatusCode());
	}

	@Test
	void getBasketsTest(){
		List<Baskets> baskets = new ArrayList<>();

		baskets.add(basket);
		when(basketsRepository.findAll()).thenReturn(baskets);
		assertEquals(1,basketService.getBaskets().size());
	}

	@Test
	void BlankBasketTest(){
		Baskets baskets = new Baskets();
		assertEquals(HttpStatus.BAD_REQUEST,basketService.addBaskets(baskets).getStatusCode());
	}

	@Test
	void deleteBasketTest(){
		//basketsRepository.save(basket);
		when(basketsRepository.existsById(basket.getBasketId())).thenReturn(true);
		assertEquals(HttpStatus.OK,basketService.deleteBasket(basket.getBasketId()).getStatusCode());
	}

	@Test
	void deleteBasketWithoutBasketTest(){
		when(basketsRepository.existsById(basket.getBasketId())).thenReturn(false);
		assertEquals(HttpStatus.BAD_REQUEST,basketService.deleteBasket(basket.getBasketId()).getStatusCode());
	}

	@Test
	void modifyBasketTest(){
		when(basketsRepository.existsById(basket.getBasketId())).thenReturn(true);
		assertEquals(HttpStatus.OK,basketService.modifyBasket(basket.getBasketId(),basket).getStatusCode());
	}

	@Test
	void modifyBasketWithoutBasketTest(){
		when(basketsRepository.existsById(basket.getBasketId())).thenReturn(false);
		assertEquals(HttpStatus.BAD_REQUEST,basketService.modifyBasket(basket.getBasketId(),basket).getStatusCode());
	}

	@Test
	void getBasketDetailsTest(){
		when(basketsRepository.findById(basket.getBasketId())).thenReturn(Optional.ofNullable(basket));
		assertEquals(basket,basketService.getBasketDetails(basket.getBasketId()).get());
	}
	@Test
	void getMappingIdsTest(){
		stocksMappingSet.add(stocksMapping);
		basket = new Baskets(1L,"basket1","basket1 desc",stocksMappingSet,null);
		List<Long> Ids = new ArrayList<>();
		Ids.add(stocksMapping.getMappingId());
		assertEquals(Ids,basketService.getMappingIds(basket));
	}
	@Test
	void addStocksTest(){
		assertEquals(HttpStatus.CREATED,stocksService.addStocks(stock).getStatusCode());
	}

	@Test
	void addStockswithLessDataTest(){
		Stocks stocks = new Stocks();
		assertEquals(HttpStatus.BAD_REQUEST,stocksService.addStocks(stocks).getStatusCode());
	}
	@Test
	void modifyStockTest(){
		Set<StocksMapping> stocksMappingSet1=new HashSet<>();
		StocksMapping stocksMapping1 = new StocksMapping();
		Stocks stocks1=new Stocks();
		stocksMapping1.setQty(2);
		stocksMapping1.setStocks(stocks1);
		stocksMappingSet1.add(stocksMapping1);
		//stocks1 = new Stocks(1L,"stock1",1200F,stocksMappingSet1);
		stocks1.setStockName("stock2");
		stocks1.setStockPrice(2000F);
		stocks1.setStocksMapping(stocksMappingSet1);
		when(stocksRepository.existsById(stock.getStockId())).thenReturn(true);
		assertEquals(HttpStatus.OK,stocksService.modifyStocks(stock.getStockId(),stocks1).getStatusCode());
	}

	@Test
	void modifyStockswithLessDataTest(){
		Stocks stocks = new Stocks();
		assertEquals(HttpStatus.BAD_REQUEST,stocksService.modifyStocks(stock.getStockId(),stocks).getStatusCode());
	}
	@Test
	void deleteStockTest(){
		when(stocksRepository.existsById(stock.getStockId())).thenReturn(true);
		assertEquals(HttpStatus.OK,stocksService.deleteStocks(stock.getStockId()).getStatusCode());
	}
	@Test
	void deleteStockwithoutStockTest(){
		when(stocksRepository.existsById(stock.getStockId())).thenReturn(false);
		assertEquals(HttpStatus.BAD_REQUEST,stocksService.deleteStocks(stock.getStockId()).getStatusCode());
	}

	@Test
	void getStocksByMappingTest(){
		List<Stocks> stocksList = new ArrayList<>();
		stocksList.add(stock);
		List<Long> list = new ArrayList<>();
		list.add(1L);
		when(stocksMappingRepository.getStockId(stock.getStockId())).thenReturn(stock.getStockId());
		when(stocksRepository.findById(stock.getStockId())).thenReturn(Optional.ofNullable(stock));
		assertEquals(stocksList,stocksService.getStocksByMapping(list));
	}

	@Test
	void getInvestmentAmountTest(){
		stocksMappingSet.add(stocksMapping);
		List<Stocks> stocksList = new ArrayList<>();
		stocksList.add(stock);
		assertEquals(4800F,stocksService.getInvestmentAmount(stocksList,basket.getBasketId()));
	}
	@Test
	void addToCartTest(){
		CartMapping cartMapping = new CartMapping(basket);
		List<CartMapping> cartMappingList = new ArrayList<>();
		cartMappingList.add(cartMapping);
		Cart cart = new Cart(101L,cartMappingList);
		List<Cart> cartList = new ArrayList<>();
		cartList.add(cart);

		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(authentication.getName()).thenReturn(user.getUserName());
		when(applicationUserRepository.findByUserName(user.getUserName())).thenReturn(user);
		when(basketsRepository.existsById(basket.getBasketId())).thenReturn(true);
		when(cartRepository.existsByUserId(user.getUserId())).thenReturn(true);
		when(cartRepository.findByUserId(user.getUserId())).thenReturn(cart);
		when(cartMappingRepository.existsByCartIdAndBasketId(cart.getCartId(),basket.getBasketId())).thenReturn(0L);
		when(cartRepository.findAll()).thenReturn(cartList);
		when(applicationUserRepository.findByUserName(user.getUserName())).thenReturn(user);

		assertEquals(HttpStatus.CREATED,cartService.addToCart(basket.getBasketId()).getStatusCode());
		assertNotEquals(HttpStatus.BAD_REQUEST,cartService.addToCart(basket.getBasketId()).getStatusCode());
	}

	@Test
	void addToCart1Test(){
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(authentication.getName()).thenReturn(user.getUserName());
		when(applicationUserRepository.findByUserName(user.getUserName())).thenReturn(user);
		when(basketsRepository.existsById(basket.getBasketId())).thenReturn(true);
		when(cartRepository.existsByUserId(user.getUserId())).thenReturn(false);
		assertEquals(HttpStatus.CREATED,cartService.addToCart(basket.getBasketId()).getStatusCode());
	}

	@Test
	void addToCartWithoutBasketIdTest(){
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(authentication.getName()).thenReturn(user.getUserName());
		when(applicationUserRepository.findByUserName(user.getUserName())).thenReturn(user);
		when(basketsRepository.existsById(basket.getBasketId())).thenReturn(false);
		assertEquals(HttpStatus.BAD_REQUEST,cartService.addToCart(basket.getBasketId()).getStatusCode());
	}
	@Test
	void deleteFromCartTest(){
		CartMapping cartMapping = new CartMapping(basket);
		List<CartMapping> cartMappingList = new ArrayList<>();
		cartMappingList.add(cartMapping);
		Cart cart = new Cart(101L,cartMappingList);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(authentication.getName()).thenReturn(user.getUserName());
		when(applicationUserRepository.findByUserName(user.getUserName())).thenReturn(user);
		when(cartMappingRepository.existsByBasketId(basket.getBasketId())).thenReturn(1L);
		when(cartRepository.findByUserId(user.getUserId())).thenReturn(cart);
		when(cartMappingRepository.cartCount(cart.getCartId())).thenReturn(1L);
		assertEquals(HttpStatus.OK,cartService.deleteFromCart(basket.getBasketId()).getStatusCode());
	}

	@Test
	void deleteFromCartWithoutBasketIdTest(){
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getName()).thenReturn(user.getUserName());
		when(applicationUserRepository.findByUserName(user.getUserName())).thenReturn(user);
		when(cartMappingRepository.existsByBasketId(basket.getBasketId())).thenReturn(0L);
		assertEquals(HttpStatus.BAD_REQUEST,cartService.deleteFromCart(basket.getBasketId()).getStatusCode());
	}

	@Test
	void getUserCartTest(){
		CartMapping cartMapping = new CartMapping(basket);
		List<CartMapping> cartMappingList = new ArrayList<>();
		cartMappingList.add(cartMapping);
		Cart cart = new Cart(101L,cartMappingList);
		List<Baskets> basketsList = new ArrayList<>();
		basketsList.add(basket);
		when(cartRepository.findByUserId(user.getUserId())).thenReturn(new Cart());
		when(cartMappingRepository.findAllByCartCartId(cart.getCartId())).thenReturn(cartMappingList);
		when(basketsRepository.findById(basket.getBasketId())).thenReturn(Optional.ofNullable(basket));
		assertEquals(basketsList,cartService.getUsersCart(user.getUserId()));
	}
}