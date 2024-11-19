import java.sql.*;
import java.util.Scanner;

public class Test {
    public static void main(String args[]) {
        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 데이터베이스 연결
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://192.168.56.101:4567/madang",
                "iyyoon", "1234"
            );

            Scanner scanner = new Scanner(System.in);
            while (true) {
        
                System.out.println("1. 데이터 삽입");
                System.out.println("2. 데이터 삭제");
                System.out.println("3. 데이터 검색");
             
                System.out.print("선택: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // 버퍼 비우기

                switch (choice) {
                    
                    case 1: // 데이터 삽입
                        System.out.println("새로운 데이터 삽입");
                        System.out.print("책 ID 입력: ");
                        int bookId = scanner.nextInt();
                        scanner.nextLine(); // 버퍼 비우기

                        System.out.print("책 이름 입력: ");
                        String bookName = scanner.nextLine();

                        System.out.print("출판사 입력: ");
                        String publisher = scanner.nextLine();

                        System.out.print("가격 입력: ");
                        int price = scanner.nextInt();
                        scanner.nextLine(); // 버퍼 비우기

                        String insertQuery = "INSERT INTO Book (bookid, bookname, publisher, price) VALUES (" +
                                             bookId + ", '" + bookName + "', '" + publisher + "', " + price + ")";
                        try {
                            Statement insertStmt = con.createStatement();
                            int insertCount = insertStmt.executeUpdate(insertQuery);
                            System.out.println(insertCount + "개의 행이 삽입되었습니다.");
                        } catch (SQLException e) {
                            System.out.println("오류 발생: " + e.getMessage());
                        }
                        break;

                    case 2: // 데이터 삭제
                        System.out.println("삭제할 데이터 입력");
                        System.out.print("책 ID 입력: ");
                        int deleteBookId = scanner.nextInt();
                        scanner.nextLine(); // 버퍼 비우기

                        String deleteQuery = "DELETE FROM Book WHERE bookid = " + deleteBookId;
                        try {
                            Statement deleteStmt = con.createStatement();
                            int deleteCount = deleteStmt.executeUpdate(deleteQuery);
                            if (deleteCount > 0) {
                                System.out.println(deleteCount + "개의 행이 삭제되었습니다.");
                            } else {
                                System.out.println("삭제할 데이터가 없습니다.");
                            }
                        } catch (SQLException e) {
                            System.out.println("오류 발생: " + e.getMessage());
                        }
                        break;
                        
                    case 3: // 검색
                        System.out.print("책 ID 또는 책 이름 입력: ");
                        String searchInput = scanner.nextLine();

                        String searchQuery;
                        if (searchInput.matches("\\d+")) { 
                            searchQuery = "SELECT * FROM Book WHERE bookid = " + searchInput;
                        } else { 
                            searchQuery = "SELECT * FROM Book WHERE bookname LIKE '%" + searchInput + "%'";
                        }

                        Statement searchStmt = con.createStatement();
                        ResultSet rs = searchStmt.executeQuery(searchQuery);
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columns = rsmd.getColumnCount();

                        while (rs.next()) {
                            for (int i = 1; i <= columns; i++) {
                                System.out.print(rs.getString(i) + " ");
                            }
                            System.out.println();
                        }
                        break;


                   
                }
            }
        } catch (Exception e) {
            System.out.println("오류 발생: " + e);
        }
    }
}
