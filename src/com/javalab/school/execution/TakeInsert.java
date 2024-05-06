package com.javalab.school.execution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 성적 데이터를 등록하는 클래스
 */
public class TakeInsert {
    // 오라클 DB에 접속하기 위한 정보
    public static void main(String[] args) {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "school", "1234");
            System.out.println("DB 접속 성공");

            registerTake(conn, scanner);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } // end of main

    private static void registerTake(Connection conn, Scanner scanner) {
        System.out.print("학생 코드: ");
        String student_id = scanner.nextLine();
        System.out.print("과목 입력: ");
        String subject = scanner.nextLine();
        System.out.print("점수: ");
        String score = scanner.nextLine();


        String sql = "INSERT INTO takes (student_id, subject, score) " +
                " VALUES (?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            // Connection 구현 객체의 prepareStatemnet 메소드에 쿼리문을 전달해서
            // 쿼리문을 실행할 수 있도록 준비를 하는 PreparedStatement 객체를 생성한다.
            // PreparedStatement 객체에 동적으로 전달받는 파라미터(?)가 있다면 이를 세팅하는
            // 과정을 거쳐야 한다.
            // PreparedStatemnet.preparesStatemnet(실행할 쿼리문) : 쿼리 실행
            pstmt = conn.prepareStatement(sql);

            // 쿼리문 실행 시에 전달할 파라미터(?)를 세팅한다.
            // setString(파라미터 인덱스 ,파라미터 값) : 파라미터 인덱스는 1부터 시작
            pstmt.setString(1, student_id);
            pstmt.setString(2, subject);
            pstmt.setString(3, score);

            // 쿼리문의 파라미터인 ? 를 채운 후 쿼리 실행
            pstmt.executeUpdate(); // 쿼리 싱행 저장/수정/삭제는 executeUpdate() 메소드 사용
            System.out.println("성적이 성공적으로 등록되었습니다");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            scanner.close();
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}