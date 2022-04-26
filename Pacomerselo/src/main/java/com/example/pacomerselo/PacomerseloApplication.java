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
            ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(PacomerseloApplication.class, args);



            RestaurantRepository restaurantRepository = configurableApplicationContext.getBean(RestaurantRepository.class);
            DishesRepository dishesRepository = configurableApplicationContext.getBean(DishesRepository.class);
            OrderRepository orderRepository = configurableApplicationContext.getBean(OrderRepository.class);
            UserRepository userRepository = configurableApplicationContext.getBean(UserRepository.class);

            Restaurant laBonita = new Restaurant("La Bonita", "Comida Original", "Mediterránea");
            Restaurant fellina = new Restaurant("Fellina", "Comida Italiana Sofisticada", "Italiana");
            Restaurant burmet = new Restaurant("Burmet", "Auténtica Comida Americana en Madrid", "Americana");
            Restaurant zielou = new Restaurant("Zielou", "Un nuevo concepto gastronómico llevado al límite.", "Internacional");
            Restaurant tagoMago = new Restaurant("Tagomago", "En sus platos encontrarás una fusión de sabores", "Internacional");
            Restaurant bizzo = new Restaurant("Bizzo", "La pizza más canalla de Madrid", "Pizza");


            restaurantRepository.save(laBonita);

            Dishes dish11 = new Dishes("Croquetas Gourmet", "6ud", 12, DishType.STARTER.label);
            Dishes dish12 = new Dishes("Ensalada de Burrata", "Con miel y trufa", 20, DishType.STARTER.label);
            Dishes dish13 = new Dishes("Solomillo de Ternera", "Con patatas baby especiadas", 25, DishType.MAIN.label);
            Dishes dish14 = new Dishes("Cappellacci", "Relleno de verduras", 15, DishType.MAIN.label);
            Dishes dish15 = new Dishes("Tarta de Calabaza", "Con violetas", 10, DishType.DESSERT.label);
            Dishes dish16 = new Dishes("Pizza de Mortadela ", "Tomate, mortadela trufada y muuuucho queso", 13, DishType.MAIN.label);
            Dishes dish17 = new Dishes("Ensaladilla de Batata", "con lingotes de ventresca y mahonesa de eneldo", 11, DishType.STARTER.label);
            Dishes dish18 = new Dishes("Mejillones de Roca", "En salsa de tomate, ajo y albahaca fresca", 11, DishType.STARTER.label);

            Dishes dish21 = new Dishes("Ensalada de Queso de Cabra", "Con tomate y rúcula", 17, DishType.STARTER.label);
            Dishes dish22 = new Dishes("Pappardelle", "Con ternera y queso", 19, DishType.MAIN.label);
            Dishes dish23 = new Dishes("Tabla embutidos", "Jamón, focaccia, ...", 9, DishType.STARTER.label);

            Dishes dish24 = new Dishes("Fagotino", "Bomba de Nutella", 9, DishType.DESSERT.label);
            Dishes dish25 = new Dishes("Pizza Burrata", "Con pomodoro, scaglie di parmagiano e rucola", 16, DishType.MAIN.label);
            Dishes dish26 = new Dishes("Fellina Tartufo", "Con trufa, queso parmesano y huevo poché", 25, DishType.MAIN.label);

            Dishes dish30 = new Dishes("Burmet Nachos", "Queso fundido, guacamole y Spicy Chicken", 13, DishType.STARTER.label);
            Dishes dish31 = new Dishes("Quesadilla", "Rellena de queso y Pull Porked", 12, DishType.STARTER.label);
            Dishes dish32 = new Dishes("Hamburguesa: Cheese", "4 quesos con Cebolla y Jalapeños", 15, DishType.MAIN.label);
            Dishes dish33 = new Dishes("Vigi Roll", "Vegano. Calabaza, cebolla y espinacas", 13, DishType.MAIN.label);
            Dishes dish34 = new Dishes("Carrot Cake", "Buttercream de chocolate blanco y almendras", 7, DishType.DESSERT.label);
            Dishes dish35 = new Dishes("Cheese Cake", "Casera al estilo Nueva York", 7, DishType.DESSERT.label);

            Dishes dish42=new Dishes("Ceviche de Corvina", "Con leche de tigre de ají-limo, cancha tostada, plátano macho y crujiente de patata violeta.", 22, DishType.STARTER.label);
            Dishes dish43=new Dishes("Tartar de Atún rojo de Balfegó", "Sashimi al natural acompañado de salsa roja especiada Tartar sobre pan brioche aliñado con kimchee coreano y aceite de soja.", 29, DishType.STARTER.label);
            Dishes dish44=new Dishes("Trufón de rabo de toro", "Sobre parmentier de patata trufada y foie", 28, DishType.MAIN.label);
            Dishes dish45=new Dishes("Udón Vegano al curry Massaman", "Con curry Massaman, setas shiitake, pak choy, tofu y cebolleta china", 22, DishType.MAIN.label);
            Dishes dish46=new Dishes("Tarta de queso Payoyo (2 pax)", "Con crujiente de regaliz, fina galleta de canela y manzana caramelizada.", 20, DishType.DESSERT.label);
            Dishes dish47=new Dishes("Tarta de chocolate", "70% de cacao con núcleo de ganache ligero de chocolate y naranja, acompañadas de grageas de colores.", 29, DishType.STARTER.label);

            Dishes dish60=new Dishes("Pulpo sobre alioli de patata", "Con aceite de pimentón y crujiente de jamón.", 10, DishType.STARTER.label);
            Dishes dish61=new Dishes("Berenjena caramelizada", "Con shitake, crema agria y romesco.", 12, DishType.STARTER.label);
            Dishes dish62=new Dishes("Lentejas melosas de marisco", "Con alioli y ajo tierno.", 17, DishType.MAIN.label);
            Dishes dish63=new Dishes("Costillar ibérico", "Cocinado a baja temperatura.", 25, DishType.MAIN.label);
            Dishes dish64=new Dishes("Tarta banofee", "Galleta, plátano, dulce de leche y nata.", 9, DishType.DESSERT.label);
            Dishes dish65=new Dishes("Tarta cremosa de lima", "Con base de galleta y nata .", 29, DishType.DESSERT.label);

            Dishes dish80=new Dishes("Bollito", "Barrita de pan al horno acompañada de nuestra salsa especial con un toque de ajo y orégano.", 5, DishType.STARTER.label);
            Dishes dish81=new Dishes("The Provolone", "Pomodoro Bizzo, provolone dulce y  un toque de orégano. Acompañado de nuestro pan de pizza artesano.", 29, DishType.STARTER.label);
            Dishes dish82=new Dishes("Pizza Curcubita", "Pomodoro Bizzo, mozzarella, calabacín marinado y un toque de parmesano.", 13, DishType.MAIN.label);
            Dishes dish83=new Dishes("Pizza Mucca", "Pomodoro Bizzo, mozzarella, costilla de ternera deshuesada aderezada con un mix de especias y queso cheddar.", 15, DishType.MAIN.label);
            Dishes dish84=new Dishes("Pizza Pitio", "Base de dulce de leche aderezada con queso gorgonzola.", 10, DishType.DESSERT.label);
            Dishes dish85=new Dishes("Pizza Celia", "Base de nutella con topping de oreo y lacasitos.", 10, DishType.DESSERT.label);


            laBonita.add(dish11);
            laBonita.add(dish12);
            laBonita.add(dish13);
            laBonita.add(dish14);
            laBonita.add(dish15);
            laBonita.add(dish16);
            laBonita.add(dish17);
            laBonita.add(dish18);

            dish11.setRestaurant(laBonita);
            dish12.setRestaurant(laBonita);
            dish13.setRestaurant(laBonita);
            dish14.setRestaurant(laBonita);
            dish15.setRestaurant(laBonita);
            dish16.setRestaurant(laBonita);
            dish17.setRestaurant(laBonita);
            dish18.setRestaurant(laBonita);

            restaurantRepository.save(fellina);
            fellina.add(dish21);
            fellina.add(dish22);
            fellina.add(dish23);
            fellina.add(dish24);
            fellina.add(dish25);
            fellina.add(dish26);


            dish21.setRestaurant(fellina);
            dish22.setRestaurant(fellina);
            dish23.setRestaurant(fellina);
            dish24.setRestaurant(fellina);
            dish25.setRestaurant(fellina);
            dish26.setRestaurant(fellina);

            restaurantRepository.save(burmet);
            burmet.add(dish30);
            burmet.add(dish31);
            burmet.add(dish32);
            burmet.add(dish33);
            burmet.add(dish34);
            burmet.add(dish35);


            dish30.setRestaurant(burmet);
            dish31.setRestaurant(burmet);
            dish32.setRestaurant(burmet);
            dish33.setRestaurant(burmet);
            dish34.setRestaurant(burmet);
            dish35.setRestaurant(burmet);


            dishesRepository.save(dish11);
            dishesRepository.save(dish12);
            dishesRepository.save(dish13);
            dishesRepository.save(dish14);
            dishesRepository.save(dish15);
            dishesRepository.save(dish16);
            dishesRepository.save(dish17);
            dishesRepository.save(dish18);
            dishesRepository.save(dish21);
            dishesRepository.save(dish22);
            dishesRepository.save(dish23);
            dishesRepository.save(dish24);
            dishesRepository.save(dish25);
            dishesRepository.save(dish26);
            dishesRepository.save(dish30);
            dishesRepository.save(dish31);
            dishesRepository.save(dish32);
            dishesRepository.save(dish33);
            dishesRepository.save(dish34);
            dishesRepository.save(dish35);

            restaurantRepository.save(zielou);
            zielou.add(dish42);
            zielou.add(dish43);
            zielou.add(dish44);
            zielou.add(dish46);
            zielou.add(dish47);

            dish42.setRestaurant(zielou);
            dish43.setRestaurant(zielou);
            dish44.setRestaurant(zielou);
            dish45.setRestaurant(zielou);
            dish46.setRestaurant(zielou);
            dish47.setRestaurant(zielou);

            dishesRepository.save(dish42);
            dishesRepository.save(dish43);
            dishesRepository.save(dish44);
            dishesRepository.save(dish45);
            dishesRepository.save(dish46);
            dishesRepository.save(dish47);

            restaurantRepository.save(tagoMago);
            tagoMago.add(dish60);
            tagoMago.add(dish61);
            tagoMago.add(dish62);
            tagoMago.add(dish63);
            tagoMago.add(dish64);
            tagoMago.add(dish65);

            dish60.setRestaurant(tagoMago);
            dish61.setRestaurant(tagoMago);
            dish62.setRestaurant(tagoMago);
            dish63.setRestaurant(tagoMago);
            dish64.setRestaurant(tagoMago);
            dish65.setRestaurant(tagoMago);

            dishesRepository.save(dish60);
            dishesRepository.save(dish61);
            dishesRepository.save(dish62);
            dishesRepository.save(dish63);
            dishesRepository.save(dish64);
            dishesRepository.save(dish65);

            restaurantRepository.save(bizzo);

            bizzo.add(dish80);
            bizzo.add(dish81);
            bizzo.add(dish82);
            bizzo.add(dish83);
            bizzo.add(dish84);
            bizzo.add(dish85);

            dish80.setRestaurant(bizzo);
            dish81.setRestaurant(bizzo);
            dish82.setRestaurant(bizzo);
            dish83.setRestaurant(bizzo);
            dish84.setRestaurant(bizzo);
            dish85.setRestaurant(bizzo);

            dishesRepository.save(dish80);
            dishesRepository.save(dish81);
            dishesRepository.save(dish82);
            dishesRepository.save(dish83);
            dishesRepository.save(dish84);
            dishesRepository.save(dish85);



            User paco = new User("Anonimo", "Paco", "Mérselo", "paco@pacomerselo.es", "oleole", true);
            User yisus = new User("Yisus", "Jesus", "Ramirez", "yisus@urjc.es", "ibaiNoob", false);

            Order order1 = new Order();
            Order order2 = new Order();
            Order order3 = new Order();


            order1.add(dish11);
            order1.add(dish13);
            order1.add(dish22);

            order2.add(dish21);
            order2.add(dish23);
            order2.add(dish11);

            order3.add(dish14);
            order3.add(dish12);

            order1.setUser(paco);
            order2.setUser(yisus);
            order3.setUser(paco);

            userRepository.save(paco);
            userRepository.save(yisus);

            orderRepository.save(order1);
            orderRepository.save(order2);
            orderRepository.save(order3);


    }
}
