package com.example.hellodelivery.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.hellodelivery.models.Product;
import com.example.hellodelivery.models.TrendingFood;
import com.example.hellodelivery.network.ProductApi;
import com.example.hellodelivery.network.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private final ProductApi productApi;

    public ProductRepository() {
        productApi = RetrofitClient.getClient().create(ProductApi.class);
    }

    public LiveData<List<Product>> getProductsByStore(String storeId) {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        productApi.getProductsByStore(storeId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<Product>> getPopularProducts() {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        productApi.getPopularProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    data.setValue(getMockAllProducts());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                data.setValue(getMockAllProducts());
            }
        });
        return data;
    }

    public List<Product> getMockAllProducts() {
        List<Product> products = new ArrayList<>();
        
        // Burger Category (ID: 1)
        products.add(new Product("b1", "Bacon BBQ Burger", "Double beef, smoked bacon, BBQ sauce.", 14.50, 18.00, "https://images.unsplash.com/photo-1594212699903-ec8a3eca50f5?w=400", "1", "s1", 4.8f, 10));
        products.add(new Product("b2", "Mushroom Swiss", "Sauteed mushrooms with melted swiss cheese.", 13.20, 0, "https://images.unsplash.com/photo-1550317138-10000687ad32?w=400", "1", "s1", 4.5f, 15));
        products.add(new Product("b3", "Spicy Zinger", "Crispy chicken breast with spicy mayo.", 11.00, 13.50, "https://images.unsplash.com/photo-1521305916504-4a1121188589?w=400", "1", "s2", 4.7f, 20));
        products.add(new Product("b4", "Veggie Delight", "Premium plant-based patty with avocado.", 12.00, 0, "https://images.unsplash.com/photo-1512152272829-e3139592d56f?w=400", "1", "s3", 4.4f, 12));
        products.add(new Product("b5", "Classic Cheeseburger", "Traditional cheeseburger with secret sauce.", 9.99, 12.00, "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400", "1", "s1", 4.6f, 30));

        // Pizza Category (ID: 2)
        products.add(new Product("p1", "Margherita", "Fresh mozzarella, basil, and tomato sauce.", 12.00, 15.00, "https://images.unsplash.com/photo-1574071318508-1cdbad80ad38?w=400", "2", "s2", 4.9f, 25));
        products.add(new Product("p2", "Pepperoni Feast", "Loaded with spicy pepperoni and cheese.", 15.50, 0, "https://images.unsplash.com/photo-1628840042765-356cda07504e?w=400", "2", "s2", 4.8f, 20));
        products.add(new Product("p3", "BBQ Chicken Pizza", "Grilled chicken with tangy BBQ sauce.", 16.00, 20.00, "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400", "2", "s2", 4.7f, 18));
        products.add(new Product("p4", "Veggie Supreme", "Bell peppers, onions, mushrooms, and olives.", 14.00, 0, "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400", "2", "s5", 4.5f, 15));
        products.add(new Product("p5", "Hawaiian", "Ham and pineapple for a sweet & savory mix.", 13.50, 0, "https://images.unsplash.com/photo-1593560708920-61dd98c46a4e?w=400", "2", "s2", 4.2f, 10));

        // Chicken Category (ID: 3)
        products.add(new Product("c1", "Fried Chicken Bucket", "8 pieces of crispy original recipe chicken.", 19.99, 25.00, "https://images.unsplash.com/photo-1562967914-608f82629710?w=400", "3", "s3", 4.8f, 50));
        products.add(new Product("c2", "Grilled Chicken Breast", "Herbed grilled chicken with side salad.", 13.50, 0, "https://images.unsplash.com/photo-1532550907401-a500c9a57435?w=400", "3", "s3", 4.6f, 20));
        products.add(new Product("c3", "Spicy Wings", "10 pieces of buffalo wings with ranch.", 12.00, 15.00, "https://images.unsplash.com/photo-1527477396000-e27163b481c2?w=400", "3", "s3", 4.7f, 40));
        products.add(new Product("c4", "Chicken Nuggets", "12 piece premium white meat nuggets.", 8.50, 0, "https://images.unsplash.com/photo-1562967962-22ef9b119934?w=400", "3", "s3", 4.4f, 60));
        products.add(new Product("c5", "Roasted Half Chicken", "Slow roasted with garlic and herbs.", 15.00, 18.00, "https://images.unsplash.com/photo-1598515214211-89d3c73ae83b?w=400", "3", "s3", 4.9f, 15));

        // Traditional Category (ID: 4)
        products.add(new Product("t1", "Doro Wat", "Spicy chicken stew with boiled egg and Injera.", 18.00, 22.00, "https://lowcarbapi.com/wp-content/uploads/2022/07/Awaze-Tibe-Egyptian-Beef-Tibs-Recipe-IG-1.jpg", "4", "s4", 5.0f, 10));
        products.add(new Product("t2", "Kitfo Special", "Minced beef with herbal butter and spices.", 20.00, 0, "https://images.unsplash.com/photo-1544025162-d76694265947?w=400", "4", "s4", 4.9f, 8));
        products.add(new Product("t3", "Beyaynetu", "Mixed vegetarian platter on Injera.", 15.00, 0, "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400", "4", "s4", 4.8f, 20));
        products.add(new Product("t4", "Beef Tibs", "Sautéed beef with onions and peppers.", 16.50, 19.00, "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?w=400", "4", "s4", 4.7f, 15));
        products.add(new Product("t5", "Shiro Wat", "Savory chickpea stew, a staple favorite.", 10.00, 0, "https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=400", "4", "s4", 4.6f, 30));

        // Desserts Category (ID: 5)
        products.add(new Product("d1", "Chocolate Lava Cake", "Warm cake with a molten center.", 7.50, 9.00, "https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=400", "5", "s5", 4.9f, 20));
        products.add(new Product("d2", "NY Cheesecake", "Creamy cheesecake with berry topping.", 8.00, 0, "https://images.unsplash.com/photo-1533134242443-d4fd215305ad?w=400", "5", "s5", 4.8f, 15));
        products.add(new Product("d3", "Apple Pie", "Classic home-style pie with cinnamon.", 6.00, 0, "https://images.unsplash.com/photo-1568571780765-9276ac8b75a2?w=400", "5", "s5", 4.5f, 12));
        products.add(new Product("d4", "Ice Cream Sundae", "Three scoops with fudge and nuts.", 6.50, 8.00, "https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=400", "5", "s5", 4.6f, 25));
        products.add(new Product("d5", "Tiramisu", "Authentic Italian coffee-flavored dessert.", 9.00, 11.00, "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400", "5", "s5", 4.7f, 10));

        // Drinks Category (ID: 6)
        products.add(new Product("dr1", "Fresh Orange Juice", "100% freshly squeezed oranges.", 4.50, 5.50, "https://images.unsplash.com/photo-1621506289937-a8e4df240d0b?w=400", "6", "s6", 4.8f, 40));
        products.add(new Product("dr2", "Iced Tea", "Refreshing house-made lemon iced tea.", 3.00, 0, "https://images.unsplash.com/photo-1556679343-c7306c1976bc?w=400", "6", "s6", 4.4f, 50));
        products.add(new Product("dr3", "Mango Smoothie", "Creamy blend of fresh mangoes.", 5.50, 7.00, "https://images.unsplash.com/photo-1623065422902-30a2ad299dd4?w=400", "6", "s6", 4.7f, 30));
        products.add(new Product("dr4", "Homemade Lemonade", "Tart and sweet with fresh mint.", 4.00, 0, "https://images.unsplash.com/photo-1523472721958-978152f4d69b?w=400", "6", "s6", 4.5f, 35));
        products.add(new Product("dr5", "Premium Coca-Cola", "Classic glass bottle 330ml.", 2.50, 0, "https://images.unsplash.com/photo-1554866585-cd94860890b7?w=400", "6", "s6", 4.9f, 100));

        // Coffee Category (ID: 7)
        products.add(new Product("cf1", "Caramel Macchiato", "Rich espresso with vanilla and caramel.", 5.20, 6.50, "https://images.unsplash.com/photo-1485808191679-5f86510681a2?w=400", "7", "s7", 4.8f, 40));
        products.add(new Product("cf2", "Flat White", "Velvety steamed milk over espresso.", 4.80, 0, "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400", "7", "s7", 4.7f, 45));
        products.add(new Product("cf3", "Iced Americano", "Smooth espresso over ice and water.", 4.00, 5.00, "https://images.unsplash.com/photo-1517701604599-bb29b565090c?w=400", "7", "s7", 4.5f, 50));
        products.add(new Product("cf4", "Cappuccino", "Perfect balance of espresso and foam.", 4.50, 0, "https://images.unsplash.com/photo-1534778101976-62847782c213?w=400", "7", "s7", 4.6f, 55));
        products.add(new Product("cf5", "Double Espresso", "A powerful kick of pure coffee.", 3.50, 0, "https://images.unsplash.com/photo-1510591509098-f4fdc6d0ff04?w=400", "7", "s7", 4.9f, 60));

        // Pasta Category (ID: 8)
        products.add(new Product("ps1", "Spaghetti Carbonara", "Traditional creamy sauce with pancetta.", 15.00, 18.50, "https://images.unsplash.com/photo-1612874742237-6526221588e3?w=400", "8", "s8", 4.9f, 20));
        products.add(new Product("ps2", "Fettuccine Alfredo", "Rich butter and parmesan sauce.", 14.00, 0, "https://images.unsplash.com/photo-1645112481338-31627878bc57?w=400", "8", "s8", 4.7f, 22));
        products.add(new Product("ps3", "Lasagna Bolognese", "Layered pasta with slow-cooked meat sauce.", 16.50, 20.00, "https://images.unsplash.com/photo-1551183053-bf91a1d81141?w=400", "8", "s8", 4.8f, 15));
        products.add(new Product("ps4", "Penne Arrabbiata", "Spicy tomato sauce with garlic and chili.", 13.00, 0, "https://images.unsplash.com/photo-1563379926898-05f4575a45d8?w=400", "8", "s8", 4.5f, 25));
        products.add(new Product("ps5", "Pesto Genovese", "Fresh basil pesto with pine nuts.", 14.50, 17.00, "https://images.unsplash.com/photo-1473093226795-af9932fe5855?w=400", "8", "s8", 4.6f, 18));

        // Sandwich Category (ID: 9)
        products.add(new Product("sw1", "Club Sandwich", "Triple decker with turkey, bacon, and egg.", 11.50, 14.00, "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?w=400", "9", "s9", 4.7f, 30));
        products.add(new Product("sw2", "Philly Cheesesteak", "Thinly sliced beef with melted provolone.", 13.00, 0, "https://images.unsplash.com/photo-1553909489-cd47e0907980?w=400", "9", "s9", 4.8f, 20));
        products.add(new Product("sw3", "Chicken Avocado Wrap", "Healthy wrap with grilled chicken.", 10.50, 13.00, "https://images.unsplash.com/photo-1509722747041-619f3830c6a3?w=400", "9", "s9", 4.5f, 25));
        products.add(new Product("sw4", "Turkey & Swiss", "Premium turkey breast on whole grain.", 9.00, 0, "https://images.unsplash.com/photo-1550507992-eb63ffee0847?w=400", "9", "s9", 4.4f, 35));
        products.add(new Product("sw5", "BLT Sandwich", "Crispy bacon, lettuce, and tomato.", 9.50, 11.50, "https://images.unsplash.com/photo-1592415499556-74fcb9f18667?w=400", "9", "s9", 4.6f, 40));

        // Healthy Category (ID: 10)
        products.add(new Product("h1", "Quinoa Buddha Bowl", "Loaded with kale, chickpeas, and tahini.", 13.50, 16.00, "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=400", "10", "s10", 4.8f, 20));
        products.add(new Product("h2", "Avocado Salmon Salad", "Fresh greens with grilled salmon.", 16.00, 0, "https://images.unsplash.com/photo-1467003909585-2f8a72700288?w=400", "10", "s10", 4.9f, 15));
        products.add(new Product("h3", "Acai Power Bowl", "Topped with berries and granola.", 11.00, 13.50, "https://images.unsplash.com/photo-1590301157890-4810ed352733?w=400", "10", "s10", 4.7f, 25));
        products.add(new Product("h4", "Zucchini Noodles", "Low carb pasta with marinara.", 12.50, 0, "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=400", "10", "s10", 4.5f, 18));
        products.add(new Product("h5", "Fruit Platter", "Assorted seasonal fresh fruits.", 9.00, 11.00, "https://images.unsplash.com/photo-1490818387583-1baba5e638af?w=400", "10", "s10", 4.6f, 30));

        // Fast Food Category (ID: 11)
        products.add(new Product("ff1", "Loaded French Fries", "With cheese sauce and bacon bits.", 6.50, 8.50, "https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=400", "11", "s11", 4.6f, 50));
        products.add(new Product("ff2", "Beef Tacos (3pcs)", "Soft shell with spicy ground beef.", 10.00, 0, "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=400", "11", "s11", 4.7f, 40));
        products.add(new Product("ff3", "Classic Hot Dog", "With mustard, onions, and relish.", 5.00, 7.00, "https://images.unsplash.com/photo-1541232399669-e34742e8736f?w=400", "11", "s11", 4.4f, 60));
        products.add(new Product("ff4", "Crispy Onion Rings", "Thick cut beer battered rings.", 6.00, 0, "https://images.unsplash.com/photo-1639024471283-03518883512d?w=400", "11", "s11", 4.5f, 45));
        products.add(new Product("ff5", "Nacho Supreme", "Large platter with all the toppings.", 12.00, 15.00, "https://images.unsplash.com/photo-1513456852971-30c0b81c9d23?w=400", "11", "s11", 4.8f, 20));

        // Seafood Category (ID: 12)
        products.add(new Product("sf1", "Grilled Salmon", "With lemon butter and asparagus.", 22.00, 28.00, "https://images.unsplash.com/photo-1467003909585-2f8a72700288?w=400", "12", "s12", 4.9f, 15));
        products.add(new Product("sf2", "Shrimp Scampi", "Garlic butter shrimp over pasta.", 18.50, 0, "https://images.unsplash.com/photo-1633504581786-316c8002b1b9?w=400", "12", "s12", 4.8f, 18));
        products.add(new Product("sf3", "Fish and Chips", "Beer battered cod with tartar sauce.", 16.00, 20.00, "https://images.unsplash.com/photo-1534422298391-e4f8c170db76?w=400", "12", "s12", 4.7f, 25));
        products.add(new Product("sf4", "Lobster Roll", "Premium lobster meat on toasted bun.", 25.00, 0, "https://images.unsplash.com/photo-1533682805518-48d1f5b8cd3a?w=400", "12", "s12", 4.9f, 10));
        products.add(new Product("sf5", "Fried Calamari", "Crispy rings with marinara sauce.", 14.00, 17.50, "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=400", "12", "s12", 4.6f, 30));

        return products;
    }

    public LiveData<List<TrendingFood>> getTrendingFoods() {
        MutableLiveData<List<TrendingFood>> data = new MutableLiveData<>();
        productApi.getTrendingFoods().enqueue(new Callback<List<TrendingFood>>() {
            @Override
            public void onResponse(Call<List<TrendingFood>> call, Response<List<TrendingFood>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    data.setValue(getMockTrendingFoods());
                }
            }

            @Override
            public void onFailure(Call<List<TrendingFood>> call, Throwable t) {
                data.setValue(getMockTrendingFoods());
            }
        });
        return data;
    }

    private List<TrendingFood> getMockTrendingFoods() {
        List<TrendingFood> list = new ArrayList<>();
        // All categories represented for full connection
        list.add(new TrendingFood("t1", "Double Cheese Burger", "Burger King", "s1", "Burger", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd", 12.99, 15.99, 4.8f, "15-20 min", false, true));
        list.add(new TrendingFood("t2", "Pepperoni Feast", "Pizza Hut", "s2", "Pizza", "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3", 18.50, 22.00, 4.7f, "25-30 min", true, true));
        list.add(new TrendingFood("t3", "Fried Chicken Bucket", "KFC", "s3", "Chicken", "https://images.unsplash.com/photo-1562967914-608f82629710", 19.99, 25.00, 4.8f, "20-30 min", false, true));
        list.add(new TrendingFood("t4", "Doro Wat Special", "Habesha Restaurant", "s4", "Traditional", "https://lowcarbapi.com/wp-content/uploads/2022/07/Awaze-Tibe-Egyptian-Beef-Tibs-Recipe-IG-1.jpg", 18.00, 22.00, 5.0f, "35-40 min", false, true));
        list.add(new TrendingFood("t5", "Chocolate Lava Cake", "Sweet Delights", "s5", "Desserts", "https://images.unsplash.com/photo-1563805042-7684c019e1cb", 7.50, 9.00, 4.9f, "15-20 min", false, true));
        list.add(new TrendingFood("t6", "Mango Smoothie", "Juice Bar", "s6", "Drinks", "https://images.unsplash.com/photo-1623065422902-30a2ad299dd4", 5.50, 7.00, 4.7f, "10-15 min", false, true));
        list.add(new TrendingFood("t7", "Caramel Macchiato", "Starbucks", "s7", "Coffee", "https://images.unsplash.com/photo-1485808191679-5f86510681a2", 5.20, 6.50, 4.8f, "5-10 min", false, true));
        list.add(new TrendingFood("t8", "Spaghetti Carbonara", "Pasta House", "s8", "Pasta", "https://images.unsplash.com/photo-1612874742237-6526221588e3", 15.00, 18.50, 4.9f, "20-25 min", false, true));
        list.add(new TrendingFood("t9", "Club Sandwich", "Subway", "s9", "Sandwich", "https://images.unsplash.com/photo-1528735602780-2552fd46c7af", 11.50, 14.00, 4.7f, "15-20 min", false, true));
        list.add(new TrendingFood("t10", "Quinoa Buddha Bowl", "Healthy Bites", "s10", "Healthy", "https://images.unsplash.com/photo-1512621776951-a57141f2eefd", 13.50, 16.00, 4.8f, "15-20 min", false, true));
        list.add(new TrendingFood("t11", "Beef Tacos (3pcs)", "Taco Bell", "s11", "Fast Food", "https://images.unsplash.com/photo-1565299585323-38d6b0865b47", 10.00, 12.00, 4.7f, "15-20 min", false, true));
        list.add(new TrendingFood("t12", "Grilled Salmon", "Seafood Grill", "s12", "Seafood", "https://images.unsplash.com/photo-1467003909585-2f8a72700288", 22.00, 28.00, 4.9f, "30-35 min", false, true));
        
        return list;
    }

    public LiveData<List<Product>> getSpecialOffers() {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        productApi.getSpecialOffers().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    List<Product> offers = getMockAllProducts().stream()
                        .filter(p -> p.getDiscountPrice() > 0)
                        .collect(Collectors.toList());
                    data.setValue(offers);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                List<Product> offers = getMockAllProducts().stream()
                    .filter(p -> p.getDiscountPrice() > 0)
                    .collect(Collectors.toList());
                data.setValue(offers);
            }
        });
        return data;
    }

    public LiveData<Product> getProductDetails(String productId) {
        MutableLiveData<Product> data = new MutableLiveData<>();
        productApi.getProductDetails(productId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<Product>> searchProducts(String query) {
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        productApi.searchProducts(query).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    // MOCK SEARCH
                    List<Product> all = getMockAllProducts();
                    List<Product> filtered = all.stream()
                            .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
                            .collect(Collectors.toList());
                    data.setValue(filtered);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // MOCK SEARCH
                List<Product> all = getMockAllProducts();
                List<Product> filtered = all.stream()
                        .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
                data.setValue(filtered);
            }
        });
        return data;
    }
}
