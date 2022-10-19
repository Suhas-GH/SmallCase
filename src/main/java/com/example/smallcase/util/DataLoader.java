package com.example.smallcase.util;

import com.example.smallcase.model.ApplicationUser;
import com.example.smallcase.model.Baskets;
import com.example.smallcase.model.Stocks;
import com.example.smallcase.model.StocksMapping;
import com.example.smallcase.repository.BasketsRepository;
import com.example.smallcase.repository.StocksRepository;
import com.example.smallcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private StocksRepository stocksRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BasketsRepository basketsRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ApplicationUser user = new ApplicationUser();
        user.setFirstName("Rohit");
        user.setLastName("Sharma");
        user.setUserName("RSharma001");
        user.setPassword("Abc@12345");
        userService.registerUser(user);

        saveStocks("Tata Consultancy Services", (float) 34.39);//1
        saveStocks("Infosys", (float) 42.72);//2
        saveStocks("Wipro", (float) 31.65);//3
        saveStocks("Suzuki", (float) 21.94);//4
        saveStocks("BMW", (float) 39.68);//5
        saveStocks("Tata Motors", (float) 29.39);//6
        saveStocks("ABN AMRO", (float) 9.20);//7
        saveStocks("adyen", (float) 1367.80);//8
        saveStocks("Aegon N.V.", (float) 4.16);//9
        saveStocks("Exor", (float) 67.24);//10
        saveStocks("ING GroeV.", (float) 9.40);//11
        saveStocks("Akzo Nobel",(float)61.96);//12
        saveStocks("Holland Colors",(float)120.5);//13
        saveStocks("Koninklijke DSM",(float)120.25);//14
        saveStocks("NX Filtration",(float)9.52);//15

        Baskets basket1 = new Baskets();
        basket1.setBasketName("House Of TATA");
        basket1.setDescription("Companies that are part of TATA group");
        Set<StocksMapping> mappingSet1 = new HashSet<>();
        mappingSet1.add(setStockMappings(15,1L));
        mappingSet1.add(setStockMappings(10,6L));
        basket1.setStocksMappings(mappingSet1);
        basketsRepository.save(basket1);

        Baskets basket2 = new Baskets();
        basket2.setBasketName("IT Tracker");
        basket2.setDescription("Companies to efficiently track and invest in IT sector");
        Set<StocksMapping> mappingSet2 = new HashSet<>();
        mappingSet2.add(setStockMappings(8,1L));
        mappingSet2.add(setStockMappings(6,2L));
        mappingSet2.add(setStockMappings(4,3L));
        basket2.setStocksMappings(mappingSet2);
        basketsRepository.save(basket2);

        Baskets basket3 = new Baskets();
        basket3.setBasketName("Automobile");
        basket3.setDescription("Companies efficiently track and invest in automobile sectors");
        Set<StocksMapping> mappingSet3 = new HashSet<>();
        mappingSet3.add(setStockMappings(8,4L));
        mappingSet3.add(setStockMappings(6,5L));
        basket3.setStocksMappings(mappingSet3);
        basketsRepository.save(basket3);

        Baskets basket4 = new Baskets();
        basket4.setBasketName("Financial Tracker");
        basket4.setDescription("Companies to efficiently track and invest in the Financial sectors");
        Set<StocksMapping> mappingSet4 = new HashSet<>();
        mappingSet4.add(setStockMappings(6,7L));
        mappingSet4.add(setStockMappings(1,8L));
        mappingSet4.add(setStockMappings(9,9L));
        mappingSet4.add(setStockMappings(8,10L));
        mappingSet4.add(setStockMappings(7,11L));
        basket4.setStocksMappings(mappingSet4);
        basketsRepository.save(basket4);

        Baskets basket5=new Baskets();
        basket5.setBasketName("Speciality Chemical");
        basket5.setDescription("Companies producing Chemicals for specific purposes");
        Set<StocksMapping>mappingSet5 = new HashSet<>();
        mappingSet5.add(setStockMappings(5,12L));
        mappingSet5.add(setStockMappings(7,13L));
        mappingSet5.add(setStockMappings(8,14L));
        mappingSet5.add(setStockMappings(3,15L));
        basket5.setStocksMappings(mappingSet5);
        basketsRepository.save(basket5);

    }

    public StocksMapping setStockMappings(int qty, Long id){
        StocksMapping stocksMappings = new StocksMapping();
        stocksMappings.setQty(qty);
        Stocks stocks = new Stocks();
        stocks.setStockId(id);
        stocksMappings.setStocks(stocks);
        return stocksMappings;
    }

    public void saveStocks(String stocksName, Float stockPrice){
        Stocks stocks = new Stocks();
        stocks.setStockName(stocksName);
        stocks.setStockPrice(stockPrice);
        stocksRepository.save(stocks);
    }
}