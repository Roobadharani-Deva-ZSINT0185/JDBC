import javax.xml.transform.Result;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception {
       rollback();
    }

    public static void readRecords()throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "Password@1";
        String query = "select * from employee";

        Connection con = DriverManager.getConnection(url, username, password);

        Statement statement = con.createStatement();

        ResultSet rs = statement.executeQuery(query);

        while(rs.next()) {
            System.out.println(rs.getInt(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getInt(3));
        }
        con.close();
    }
    public static void insertRecords()throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "Password@1";
        String query = "insert into employee values (4,'shalini',20000)";

        Connection con = DriverManager.getConnection(url, username, password);

        Statement statement = con.createStatement();

        int numberOfRowsAffected = statement.executeUpdate(query);

        System.out.println(numberOfRowsAffected);
        con.close();
    }
    public static void insertVar()throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "Password@1";

        int id=5;
        String name="shalini";
        int salary=25000;
        String query = "insert into employee values ("+id+",'"+name+"',"+salary+");";

        Connection con = DriverManager.getConnection(url, username, password);

        Statement statement = con.createStatement();

        int numberOfRowsAffected = statement.executeUpdate(query);

        System.out.println("numberOfRowsAffected"+numberOfRowsAffected);
        con.close();
    }
    public static void insertUsingPrSt()throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "Password@1";

        int id=6;
        String name="rupa";
        int salary=25000;
        String query = "insert into employee values (?,?,?);";

        Connection con = DriverManager.getConnection(url, username, password);

        PreparedStatement pst=con.prepareStatement(query);

        pst.setInt(1,id);
        pst.setString(2,name);
        pst.setInt(3,salary);

        int numberOfRowsAffected = pst.executeUpdate();

        System.out.println("numberOfRowsAffected using preparedStatement"+numberOfRowsAffected);
        con.close();
    }
    public static void deleteRecordsById()throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "Password@1";

        int id=6;

        String query = "Delete from employee where emp_id="+id;

        Connection con = DriverManager.getConnection(url, username, password);

        Statement st=con.createStatement();

        int numberOfRowsAffected = st.executeUpdate(query);

        System.out.println("numberOfRows deleted"+numberOfRowsAffected);
        con.close();
    }
    public static void updateRecord()throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "Password@1";

        int id=1;
        int salary=150000;

        String query = "UPDATE employee SET salary = ? WHERE emp_id = ?";

        Connection con = DriverManager.getConnection(url, username, password);

        PreparedStatement pst=con.prepareStatement(query);

        pst.setInt(1,salary);
        pst.setInt(2,id);
        int numberOfRowsAffected = pst.executeUpdate();

        System.out.println("numberOfRows updated"+numberOfRowsAffected);
        con.close();
    }
    public static void callStoreProcedure()throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "Password@1";

        Connection con = DriverManager.getConnection(url, username, password);

        CallableStatement cs=con.prepareCall("{call GetEmp()}");

        ResultSet rs=cs.executeQuery();
        while(rs.next()) {
            System.out.println("id: "+rs.getInt(1));
            System.out.println("name : "+rs.getString(2));
            System.out.println("salary: "+rs.getInt(3));
        }
        con.close();
    }
    public static void callStoreProcedureWithInputParam()throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "Password@1";

        int id=1;

        Connection con = DriverManager.getConnection(url, username, password);

        CallableStatement cst=con.prepareCall("{call GetEmpById(?)}");

        cst.setInt(1,id);

        ResultSet rs=cst.executeQuery();

        while(rs.next()) {
            System.out.println("id: "+rs.getInt(1));
            System.out.println("name : "+rs.getString(2));
            System.out.println("salary: "+rs.getInt(3));
        }
        con.close();
    }
    public static void callStoreProcedureWithInputAndOutputParam()throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "Password@1";

        int id=1;

        Connection con = DriverManager.getConnection(url, username, password);

        CallableStatement cst=con.prepareCall("{call GetENameById(?,?)}");

        cst.setInt(1,id);
        cst.registerOutParameter(2, Types.VARCHAR);

        cst.executeUpdate();

        System.out.println(cst.getString(2));
        con.close();
    }
    public static void autoCommit()throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "Password@1";
        String query1="update employee set salary=100000 where emp_id=1";
        String query2="updat employee set ename='oviya' where emp_id=2";

        Connection con = DriverManager.getConnection(url, username, password);

        Statement st=con.createStatement();

        int num1=st.executeUpdate(query1);
        System.out.println(num1);
        int num2=st.executeUpdate(query2);
        System.out.println(num2);

        con.close();
    }
    public static void avoidAutoCommit()throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "Password@1";
        String query1="update employee set salary=26700 where emp_id=1";
        String query2="update employee set ename='oviya' where emp_id=2";

        Connection con = DriverManager.getConnection(url, username, password);
        con.setAutoCommit(false);

        Statement st=con.createStatement();

        int num1=st.executeUpdate(query1);
        System.out.println(num1);
        int num2=st.executeUpdate(query2);
        System.out.println(num2);
        if(num1>0&&num2>0){
            con.commit();
        }
        con.close();
    }
    public static void batchQuery()throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "Password@1";
        String query1="update employee set salary=26700 where emp_id=1";
        String query2="update employee set ename='oviya' where emp_id=2";
        String query3="update employee set salary=300000 where emp_id=3";
        String query4="update employee set salary=2000 where emp_id=4";
        Connection con = DriverManager.getConnection(url, username, password);

        Statement st=con.createStatement();
        st.addBatch(query1);
        st.addBatch(query2);
        st.addBatch(query3);
        st.addBatch(query4);

        int[] res=st.executeBatch();

        for(int i:res){
            System.out.println("row affected:"+i);
        }
        con.close();
    }
    public static void rollback()throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "Password@1";
        String query1="update employee set salary=40000 where emp_id=1";
        String query2="update employee set salary=40000 where emp_id=2";
        String query3="updat employee set salary=40000 where emp_id=3";
        String query4="update employee set salary=40000 where emp_id=4";
        Connection con = DriverManager.getConnection(url, username, password);
        con.setAutoCommit(false);

        Statement st=con.createStatement();

        st.addBatch(query1);
        st.addBatch(query2);
        st.addBatch(query3);
        st.addBatch(query4);

        int[] res=st.executeBatch();

        for(int i:res){
            if(i>0){
                continue;
            }
            else{
                con.rollback();
            }
        }
        con.commit();
        con.close();
    }
}