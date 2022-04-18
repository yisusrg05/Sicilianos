package com.example.pacomerselo;

import com.example.pacomerselo.Entities.*;
import com.example.pacomerselo.Repositories.Dishes.DishesRepository;
import com.example.pacomerselo.Repositories.Order.OrderRepository;
import com.example.pacomerselo.Repositories.Restaurant.RestaurantRepository;
import com.example.pacomerselo.Repositories.User.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PacomerseloApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext=SpringApplication.run(PacomerseloApplication.class, args);
        /*
        RestaurantRepository restaurantRepository= configurableApplicationContext.getBean(RestaurantRepository.class);
        DishesRepository dishesRepository= configurableApplicationContext.getBean(DishesRepository.class);
        OrderRepository orderRepository= configurableApplicationContext.getBean(OrderRepository.class);
        UserRepository userRepository= configurableApplicationContext.getBean(UserRepository.class);

        Restaurant laBonita=new Restaurant("La Bonita","Comida Original","Mediterránea");
        Restaurant fellina=new Restaurant("Fellina","Comida Italiana Sofisticada","Italiana");

        restaurantRepository.save(laBonita);

        Dishes dish11=new Dishes("Croquetas Gourmet", "6ud",12, DishType.STARTER.label);
        Dishes dish12=new Dishes("Ensalada de Burrata", "Con miel y trufa",20,DishType.STARTER.label);
        Dishes dish13=new Dishes("Solomillo de Ternera", "Con patatas baby especiadas",25,DishType.MAIN.label);
        Dishes dish14=new Dishes("Cappellacci", "Relleno de verduras",15,DishType.MAIN.label);
        Dishes dish21=new Dishes("Ensalada de Queso de Cabra", "Con tomate y rúcula",17,DishType.STARTER.label);
        Dishes dish22=new Dishes("Pappardelle", "Con ternera y queso",19,DishType.MAIN.label);
        Dishes dish23= new Dishes("Tabla embutidos", "Jamón, focaccia, ...",9,DishType.DESSERT.label);

        laBonita.add(dish11);
        laBonita.add(dish12);
        laBonita.add(dish13);
        laBonita.add(dish14);

        dish11.setRestaurant(laBonita);
        dish12.setRestaurant(laBonita);
        dish13.setRestaurant(laBonita);
        dish14.setRestaurant(laBonita);
        restaurantRepository.save(fellina);
        fellina.add(dish21);
        fellina.add(dish22);
        fellina.add(dish23);

        dish21.setRestaurant(fellina);
        dish22.setRestaurant(fellina);
        dish23.setRestaurant(fellina);

        dishesRepository.save(dish11);
        dishesRepository.save(dish12);
        dishesRepository.save(dish13);
        dishesRepository.save(dish14);
        dishesRepository.save(dish21);
        dishesRepository.save(dish22);
        dishesRepository.save(dish23);

        User admin=new User("Anonimo","Paco","Mérselo","paco@pacomerselo.es","oleole");
        User yisus= new User("Yisus","Jesus","Ramirez","yisus@urjc.es","ibaiNoob");


        Order order1= new Order();
        Order order2= new Order();
        Order order3= new Order(34);

        order1.add(dish11);
        order1.add(dish13);
        order1.add(dish22);

        order2.add(dish21);
        order2.add(dish23);
        order2.add(dish11);

        order1.setUser(admin);
        order2.setUser(yisus);

        userRepository.save(admin);
        userRepository.save(yisus);

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

         */
    }

}
