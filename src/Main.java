import com.alibaba.druid.pool.DruidDataSourceFactory;
import pojo.Account;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main  {
    public static void main(String[] args) throws Exception {
        Connection conn = start();
        CD(conn);
    }

    public static Connection start() throws Exception {
        Properties props = new Properties();
        props.load(Files.newInputStream(Paths.get("src/druid.properties")));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(props);
        return dataSource.getConnection();
    }

    public static void CD(Connection conn) throws SQLException {
        System.out.println("1.添加菜品\t\t\t\t2.删除菜品\t\t\t\t3.修改菜品");
        System.out.println("4.查询单个菜品\t\t\t5.查询所有菜品\t\t\t6.退出");
        Scanner scanner = new Scanner(System.in);
        System.out.print("请选择功能：");
        if (scanner.hasNextInt()) {
            int str2 = scanner.nextInt();
            switch (str2) {
                case 1:
                    insert(conn);
                    CD(conn);
                    break;
                case 2:
                    delete(conn);
                    CD(conn);
                    break;
                case 3:
                    update(conn);
                    CD(conn);
                    break;
                case 4:
                    print(conn,"one");
                    CD(conn);
                    break;
                case 5:
                    print(conn,"all");
                    CD(conn);
                    break;
                case 6:
                    break;

            }
        }
        scanner.close();
    }

    public static void print(Connection conn, String way) throws SQLException {
        List<Account> accounts;

        if (way.equals("all")) {
            accounts = select_all(conn);
        }else{
            accounts = select(conn);
        }

        if (accounts.isEmpty()) {
            System.out.println("未查询到该菜品!");
        }else{
            System.out.println("*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*");
            System.out.printf("%-10s", "id");
            System.out.printf("%-10s", "meal_name");
            System.out.printf("%-10s", "price");
            System.out.print("\n");
            for (Account account : accounts) {
                System.out.printf("%-10s", account.getId());
                System.out.printf("%-10s", account.getMeal_name());
                System.out.printf("%-10.2f", account.getPrice());
                System.out.print("\n");
            }
            System.out.println("*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*");
        }
    }


    public static void insert(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        String meal_name = "";
        float price = (float) 0;

        System.out.print("请输入需要添加的菜名：");
        if (scanner.hasNextLine()) {meal_name = scanner.nextLine();}

        System.out.print("请输入对应的价格：");
        if (scanner.hasNextFloat()) {price = scanner.nextFloat();}


        String sql = "insert into menu(meal_name, price) values (?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, meal_name);
        pstmt.setFloat(2, price);

        int count = pstmt.executeUpdate();

        if (count > 0) {
            System.out.println("加入成功！");
        }else{
            System.out.println("加入失败！");
        }

        pstmt.close();
    }

    public static void delete(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        int id = 0;

        System.out.print("请输入需要删除菜品的ID：");
        if (scanner.hasNextInt()) {id = scanner.nextInt();}


        String sql = "delete from menu where id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, id);

        int count = pstmt.executeUpdate();

        if (count > 0) {
            System.out.println("删除成功！");
        }else{
            System.out.println("删除失败！");
        }

        pstmt.close();
    }

    public static void update(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        int id = 0;
        String meal_name = "";
        float price = (float) 0;

        System.out.print("请输入需要修改菜名的ID：");
        if (scanner.hasNextInt()) {id = scanner.nextInt();}

        System.out.print("请输入修改后的菜名：");
        if (scanner.hasNextLine()) {meal_name = scanner.nextLine();}

        System.out.print("请输入修改后的价格：");
        if (scanner.hasNextFloat()) {price = scanner.nextFloat();}


        String sql = "update menu set meal_name = ?, price = ? where id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, meal_name);
        pstmt.setFloat(2, price);
        pstmt.setInt(3, id);

        int count = pstmt.executeUpdate();

        if (count > 0) {
            System.out.println("修改成功！");
        }else{
            System.out.println("修改失败！");
        }

        pstmt.close();
    }


    public static List<Account> select_all(Connection conn) throws SQLException {
        String sql = "select * from menu";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        List<Account> accounts = new ArrayList<>();

        while (rs.next()) {
            Account account = new Account();

            int id = rs.getInt("id");
            String meal_name = rs.getString("meal_name");
            Float price = rs.getFloat("price");

            account.setId(id);
            account.setMeal_name(meal_name);
            account.setPrice(price);

            accounts.add(account);
        }
        rs.close();
        pstmt.close();
        return accounts;
    }


    public static List<Account> select(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        String meal_name_s = "";

        System.out.print("请输入需要查询的菜名：");
        if (scanner.hasNextLine()) {meal_name_s = scanner.nextLine();}

        String sql = "select * from menu where meal_name = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, meal_name_s);

        ResultSet rs = pstmt.executeQuery();
        List<Account> accounts = new ArrayList<>();

        while (rs.next()) {
            Account account = new Account();

            int id = rs.getInt("id");
            String meal_name = rs.getString("meal_name");
            Float price = rs.getFloat("price");

            account.setId(id);
            account.setMeal_name(meal_name);
            account.setPrice(price);

            accounts.add(account);
        }
        rs.close();
        pstmt.close();
        return accounts;
    }
}