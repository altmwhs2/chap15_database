package com.javalab.school.execution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 학생 데이터를 삭제하는 클래스
 * 복붙 해서 13 25 39 42 46 52 58 60 주석 보고 수정하면 됨!
 * 나머지도 비슷한 식으로 수정 해야할거 같은 부분은 수정 하면 됨!
 */
public class StudentDelete { // 클래스 이름 맞추고 ex) 교수면 ProfessorDelete
    // 오라클 DB에 접속해서 하기 위한 정보
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");

            System.out.println("DB 접속 성공");

            deleteStudent(conn, scanner); // 메소드 이름도 바꿔줘야함 ex) 교수면 deleteProfessor
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } // end of main

    private static void deleteStudent(Connection conn, Scanner scanner) { // 24줄 메소드 이름으로 수정
        PreparedStatement pstmt = null;
        try {
            System.out.println("삭제할 학생의 ID를 입력하세요: "); // 여기도 출력문 바꿔주면 좋고
            String id = scanner.nextLine();

            // SQL 쿼리문 작성
            String sql = "DELETE FROM student WHERE student_id = ?"; // 교수 테이블의 교수 ID를 찾아야 하니까 이 부분도 수정

            // PreparedStatement 생성
            pstmt = conn.prepareStatement(sql);

            // PreparedStatement에 파라미터 설정
            pstmt.setString(1, id); // 42줄에서 여기 작성된 코드로 보면 삭제할 학생의 ID 입력한 걸 id에 저장 했으므로 여기 코드도 1, id로 나오는거라 위에서 수정 했으면 여기도 수정 해야함

            // SQL 실행
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("학생 정보가 성공적으로 삭제되었습니다."); // 여기도 출력문 교수에 맞춰서 수정하면 좋고
            } else {
                System.out.println("해당 ID의 학생을 찾을 수 없습니다."); // 요짝도
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}