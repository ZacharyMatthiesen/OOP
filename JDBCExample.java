package com.cstaley.dbcourse.jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;
import java.util.Date;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class JDBCExample {
   static public class Product {
      String id;
      String flavor;
      String kind;
      Date when;
      int count;
      
      public Product(String i, String f, String k, Date w, int c) {
         id = i; flavor = f; kind = k; when = w; count = c;
      }
   }
   
   /* Traditional version of general closer
   static void closeEm(Connection cnc, Statement stm, ResultSet rst) {
      if (rst != null)
         try {rst.close();} catch (Exception err) {}
      if (stm != null)
         try {stm.close();} catch (Exception err) {}
      if (cnc != null)
         try {cnc.close();} catch (Exception err) {}
   }*/
   
   // More powerful version, closer to C# IDisposable behavior
   static void closeEm(Object... toClose) {
      for (Object obj: toClose)
         if (obj != null)
            try {
               obj.getClass().getMethod("close").invoke(obj);
            } catch (Throwable t) {System.out.println("Log bad close");}
   }

   // Return ids for any customers with name |first| |last|
   static List<Integer> getCustomer(Connection cnc, String first, String last) 
    throws SQLException {
      Statement stm = null;
      ResultSet rst = null;
      List<Integer> rtn = new LinkedList<Integer>();
      
      try {
         stm = cnc.createStatement();
         rst = stm.executeQuery("select id from Customer where firstName = '" +
          first + "' and lastName = '" + last + "'");
         while (rst.next())
            rtn.add(rst.getInt(1));
         
         return rtn;
      }
      finally {
         closeEm(rst, stm);
      }
   }
   
   // Return all products purchased by customer |cId|, with quantities purchased
   // in descending order.
   static public List<Product> getPrefs(Connection cnc, int cId)
    throws SQLException {
      List<Product> rtn = new LinkedList<Product>();
      ResultSet rst = null;
      PreparedStatement pst = null;
      
      try {
         pst = cnc.prepareStatement("select p.id, kind, flavor, sum(qty), " +
          "max(purchaseDate) from Receipt join LineItem on receiptId = id " +
          "join Product p on productId = p.id where customerId = ? " +
          "group by p.id order by sum(qty) desc, p.id");
         pst.setInt(1, cId);
         rst = pst.executeQuery();
   
         while (rst.next())
            rtn.add(new Product(rst.getString(1), rst.getString(3),
             rst.getString(2), rst.getDate(5), rst.getInt(4)));
         
         return rtn;
      }
      finally {
         closeEm(rst, pst);
      }
   }
   
   // Use |cnc| to make purchases in quantities as indicated by |purchases|
   // for customer |cId|
   static void makePurchases(Connection cnc, int cId,
    Map<String, Integer> purchases) throws SQLException {
      PreparedStatement pStm = null;
      Statement stm = null;
      ResultSet rst = null;
      int rId, lineNum = 1;
      Formatter fmt = new Formatter(new StringBuilder
       ("insert into LineItem(receiptId, lineNum, productId, qty) values"));

      try {
         pStm = cnc.prepareStatement("insert into Receipt(purchaseDate, " +
          "customerId) values (now(), ?)", Statement.RETURN_GENERATED_KEYS);
         pStm.setInt(1, cId);
         if (pStm.executeUpdate() > 0) {
            rst = pStm.getGeneratedKeys();
            rst.next();
            rId = rst.getInt(1);
               
            for (Map.Entry<String, Integer> ent: purchases.entrySet()) { 
               fmt.format("%c(%d, %d, '%s', %d)", lineNum == 1 ? ' ' : ',', rId,
                lineNum++, ent.getKey(), ent.getValue());
            }
            stm = cnc.createStatement();
            if (stm.executeUpdate(fmt.toString()) != purchases.size()
             
             || stm.executeUpdate("update LineItem join Product " +
             "on id = productId set extPrice = qty * price " +
             "where receiptId = " + rId) != purchases.size() 
             
             || stm.executeUpdate("update Receipt set total = (select " +
             "sum(extPrice) from LineItem where receiptId = id) where " +
             "id = " + rId) != 1)
               throw new SQLException("Error creating line items.");
         }
         else
            throw new SQLException("Error creating receipt.");
      }
      finally {
         closeEm(fmt, rst, stm, pStm);
      }
   }
   
   public static void main(String[] args) {
      Connection cnc = null;
      String first, last, pId;
      int cId;
      List<Integer> ids;
      Scanner in = new Scanner(System.in);
      Map<String, Integer> purchases = new TreeMap<String, Integer>();
      
      try {
         cnc = DriverManager.getConnection(args[0], args[1], args[2]);
         System.out.print("Enter first and last name: ");
         first = in.next();
         last = in.next();
         ids = getCustomer(cnc, first, last);
         if (ids.isEmpty()) {
            System.out.println("I don't know who you are.");
            return;
         }
         cId = ids.get(0);
         if (ids.size() > 1)
            System.out.printf("Using id %d. Hope that's you!\n", cId);
         for (Product p: getPrefs(cnc, cId))
            System.out.printf("%s %s %s bought %d times, last time on %tF\n",
             p.id, p.flavor, p.kind, p.count, p.when);
         
         System.out.print("Enter product ids: ");
         while (in.hasNext()) {
            pId = in.next();
            purchases.put(pId, purchases.containsKey(pId) ? 
             purchases.get(pId) + 1 : 1);
         }
         if (!purchases.isEmpty())
            makePurchases(cnc, cId, purchases);
      }
      catch (SQLException err) {
         System.out.println(err.getMessage());
      }
      finally {
         closeEm(cnc, in);
      }
   }
}