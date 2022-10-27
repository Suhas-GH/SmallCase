package com.abnamro.smallcase;

import com.abnamro.smallcase.model.*;
import com.abnamro.smallcase.repository.*;
import com.abnamro.smallcase.service.UserService;
import com.abnamro.smallcase.model.*;
import com.abnamro.smallcase.repository.*;
import com.abnamro.smallcase.service.BasketService;
import com.abnamro.smallcase.service.CartService;
import com.abnamro.smallcase.service.StocksService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
        assertEquals(user.getUserId(),userService.getUser());
    }

    @Test
    void registerUserTest(){
        //when(applicationUserRepository.save(user)).thenReturn(user);
        assertEquals(true,userService.registerUser(user));
    }

    @Test
    void registerUserWithLessInfoTest(){
        ApplicationUser applicationUser1 = new ApplicationUser();
        applicationUser1.setFirstName("Rohit");
        applicationUser1.setLastName("Sharma");//userName
        applicationUser1.setPassword("123");
        assertEquals(false,userService.registerUser(applicationUser1));
    }

    @Test
    void doNotRegisterExistingUser(){
        when(applicationUserRepository.existsByUserName(user.getUserName())).thenReturn(true);
        assertEquals(false,userService.registerUser(user));
    }
    @Test
    void addBasketTest(){
        stocksMappingSet.add(stocksMapping);
        List<Long> Ids = basket.getStocksMappings().stream().map(x -> x.getStocks().getStockId())
                .collect(Collectors.toList());
        long count = Ids.stream().count();
        when(stocksRepository.checkStocks(Ids)).thenReturn((int)count);
        basketService.addBaskets(basket);
        verify(basketsRepository).save(basket);
    }

    @Test
    void getBasketsTest(){
        List<Baskets> baskets = new ArrayList<>();
        baskets.add(basket);
        when(basketsRepository.findAll()).thenReturn(baskets);
        assertEquals(baskets.size(),basketService.getBaskets().size());
    }

    @Test
    void BlankBasketTest(){
        Baskets baskets = new Baskets();
        //assertEquals(HttpStatus.BAD_REQUEST,basketService.addBaskets(baskets).getStatusCode());
        basketService.addBaskets(baskets);
        verify(basketsRepository,times(0)).save(baskets);
    }

    @Test
    void deleteBasketTest(){
        //basketsRepository.save(basket);
        when(basketsRepository.existsById(basket.getBasketId())).thenReturn(true);
        basketService.deleteBasket(basket.getBasketId());
        verify(basketsRepository,times(1)).deleteById(basket.getBasketId());
    }

    @Test
    void deleteBasketWithoutBasketTest(){
        when(basketsRepository.existsById(basket.getBasketId())).thenReturn(false);
        basketService.deleteBasket(basket.getBasketId());
        verify(basketsRepository,times(0)).deleteById(basket.getBasketId());
    }

    @Test
    void modifyBasketTest(){
        stocksMappingSet.add(stocksMapping);
        List<Long> Ids = basket.getStocksMappings().stream().map(x -> x.getStocks().getStockId())
                .collect(Collectors.toList());
        long count = Ids.stream().count();
        when(stocksRepository.checkStocks(Ids)).thenReturn((int)count);
        when(basketsRepository.existsById(basket.getBasketId())).thenReturn(true);
        basketService.modifyBasket(basket.getBasketId(),basket);
        verify(basketsRepository,times(1)).updateDetails("basket1","basket1 desc",1L);
        verify(stocksMappingRepository,times(1)).saveMappings(4,1L,1L);
    }

    @Test
    void modifyBasketWithoutBasketTest(){
        when(basketsRepository.existsById(basket.getBasketId())).thenReturn(false);
        //assertEquals(HttpStatus.BAD_REQUEST,basketService.modifyBasket(basket.getBasketId(),basket).getStatusCode());
        basketService.modifyBasket(basket.getBasketId(),basket);
        verify(basketsRepository,times(0)).deleteById(basket.getBasketId());
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
        stocksService.addStocks(stock);
        verify(stocksRepository,times(1)).save(stock);
    }

    @Test
    void addStockswithLessDataTest(){
        Stocks stocks = new Stocks();
        stocksService.addStocks(stocks);
        verify(stocksRepository,times(0)).save(stocks);
    }
    @Test
    void modifyStockTest(){
        Set<StocksMapping> stocksMappingSet1=new HashSet<>();
        StocksMapping stocksMapping1 = new StocksMapping();
        Stocks stocks1=new Stocks();
        stocksMapping1.setQty(2);
        stocksMapping1.setStocks(stocks1);
        stocksMappingSet1.add(stocksMapping1);
        stocks1 = new Stocks(2L,"stock2",2000F,stocksMappingSet1);
        when(stocksRepository.existsById(stock.getStockId())).thenReturn(true);
        stocksService.modifyStocks(stock.getStockId(),stocks1);
        verify(stocksRepository,times(1)).modifyStocks(stocks1.getStockName(),stocks1.getStockPrice(),stock.getStockId());
    }

    @Test
    void modifyStockswithLessDataTest(){
        Stocks stocks = new Stocks();
        stocksService.modifyStocks(stock.getStockId(),stocks);
        verify(stocksRepository,times(0)).modifyStocks(stocks.getStockName(),stocks.getStockPrice(),stock.getStockId());
    }
    @Test
    void deleteStockTest(){
        when(stocksRepository.existsById(stock.getStockId())).thenReturn(true);
        stocksService.deleteStocks(stock.getStockId());
        verify(stocksRepository,times(1)).deleteById(stock.getStockId());
    }
    @Test
    void deleteStockwithoutStockTest(){
        when(stocksRepository.existsById(stock.getStockId())).thenReturn(false);
        stocksService.deleteStocks(stock.getStockId());
        verify(stocksRepository,times(0)).deleteById(stock.getStockId());	}

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
        Cart cart = new Cart(1L,1L,cartMappingList);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user.getUserName());
        when(applicationUserRepository.findByUserName(user.getUserName())).thenReturn(user);
        when(basketsRepository.existsById(basket.getBasketId())).thenReturn(true);
        when(cartRepository.existsByUserId(user.getUserId())).thenReturn(true);
        when(cartRepository.findByUserId(user.getUserId())).thenReturn(cart);
        when(cartMappingRepository.existsByCartIdAndBasketId(1L,1L)).thenReturn(0L);
        when(cartMappingRepository.save(cartMapping)).thenReturn(cartMapping);
        cartService.addToCart(basket.getBasketId());

        assertEquals(cartMapping,cartMappingRepository.save(cartMapping));
    }

    @Test
    void addToCart1Test(){
        Baskets basket = new Baskets(1L);
        CartMapping cartMapping = new CartMapping(basket);
        List<CartMapping> cartMappingList = new ArrayList<>();
        cartMappingList.add(cartMapping);
        Cart cart = new Cart(1L,cartMappingList);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user.getUserName());
        when(applicationUserRepository.findByUserName(user.getUserName())).thenReturn(user);

        when(basketsRepository.existsById(basket.getBasketId())).thenReturn(true);
        when(cartRepository.existsByUserId(1L)).thenReturn(false);

        cartService.addToCart(1L);
        when(cartRepository.save(cart)).thenReturn(cart);
        assertEquals(cart,cartRepository.save(cart));
    }

    @Test
    void addToCartWithoutBasketIdTest(){
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user.getUserName());
        when(applicationUserRepository.findByUserName(user.getUserName())).thenReturn(user);
        when(basketsRepository.existsById(basket.getBasketId())).thenReturn(false);
        cartService.addToCart(basket.getBasketId());
        verify(basketsRepository,times(0)).save(basket);
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
        cartService.deleteFromCart(basket.getBasketId());
        verify(cartRepository,times(1)).deleteById(cart.getCartId());
    }

    @Test
    void deleteFromCartWithoutBasketIdTest(){
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user.getUserName());
        when(applicationUserRepository.findByUserName(user.getUserName())).thenReturn(user);
        when(cartMappingRepository.existsByBasketId(basket.getBasketId())).thenReturn(0L);
        //assertEquals(HttpStatus.BAD_REQUEST,cartService.deleteFromCart(basket.getBasketId()).getStatusCode());
        cartService.deleteFromCart(basket.getBasketId());
        verify(cartMappingRepository,times(0)).deleteBasketFromCart(basket.getBasketId(),1L);
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
