package com.javalab.school.execution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 과목 데이터를 수정하는 클래스
 */
public class ProfessorUpdate {
    // 오라클 DB에 접속해서 하기 위한 정보
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");

            System.out.println("DB 접속 성공");

            updateProfessor(conn, scanner);
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

    private static void updateProfessor(Connection conn, Scanner scanner) {
        System.out.println("수정할 교수의 ID를 입력하세요: ");
        String id = scanner.nextLine();
        // 사용자로부터 입력 받기
        System.out.print("새 주민 번호: ");
        String jumin = scanner.nextLine();
        System.out.println("이름 : ");
        String name = scanner.nextLine();
        System.out.println("새 학과 코드 : ");
        int department_id = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 처리
        System.out.println("새 직급 : ");
        String grade = scanner.nextLine();
        System.out.println("새 입사일: ");
        String hiredate = scanner.nextLine();
        PreparedStatement pstmt = null;

        try {
            // SQL 쿼리문 작성
            String sql = "UPDATE professor SET jumin = ?, name = ?, department_id = ?, grade = ?, hiredate = ? WHERE professor_id = ?";
            pstmt = conn.prepareStatement(sql);

            // PreparedStatement에 파라미터 설정
            pstmt.setString(1, jumin);
            pstmt.setString(2, name);
            pstmt.setInt(3, department_id);
            pstmt.setString(4, grade);
            pstmt.setString(5, hiredate);
            pstmt.setString(6, id);

            // SQL 실행
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("교수 정보가 성공적으로 업데이트 되었습니다.");
            } else {
                System.out.println("해당 ID의 교수를 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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