package org.sopt;

import org.sopt.controller.MemberController;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.exception.handler.ExceptionHandler;
import org.sopt.repository.FileMemberRepository;
import org.sopt.repository.MemberRepository;
import org.sopt.service.MemberService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        MemberRepository memberRepository = new FileMemberRepository("member.csv");
        MemberService memberService = new MemberService(memberRepository);
        MemberController memberController = new MemberController(memberService);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n✨ --- DIVE SOPT 회원 관리 서비스 --- ✨");
            System.out.println("---------------------------------");
            System.out.println("1️⃣. 회원 등록 ➕");
            System.out.println("2️⃣. ID로 회원 조회 🔍");
            System.out.println("3️⃣. 전체 회원 조회 📋");
            System.out.println("4️⃣. 회원 삭제 🗑️");
            System.out.println("5️⃣. 종료 🚪");
            System.out.println("---------------------------------");
            System.out.print("메뉴를 선택하세요: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> ExceptionHandler.execute(() -> {
                    System.out.print("등록할 회원 이름을 입력하세요: ");
                    String name = scanner.nextLine();
                    Member.validateName(name);

                    System.out.print("생년월일을 입력하세요 (YYYYMMDD): ");
                    String birthDateStr = scanner.nextLine();
                    LocalDate birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
                    Member.validateBirthDate(birthDate);
                    Member.validateAge(birthDate);

                    System.out.print("이메일을 입력하세요: ");
                    String email = scanner.nextLine();
                    Member.validateEmail(email);

                    System.out.print("성별을 입력하세요 (남성/여성): ");
                    String genderInput = scanner.nextLine();
                    Gender gender = Gender.fromString(genderInput);

                    Long createdId = memberController.createMember(name, birthDate, email, gender);
                    System.out.println("✅ 회원 등록 완료 (ID: " + createdId + ")");
                });

                case "2" -> ExceptionHandler.execute(() -> {
                    System.out.print("조회할 회원 ID를 입력하세요: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    Member member = memberController.findMemberById(id);
                    System.out.println("✅ 조회된 회원: ID=" + member.getId() + ", 이름=" + member.getName());
                });

                case "3" -> {
                    List<Member> allMembers = memberController.getAllMembers();
                    if (allMembers.isEmpty()) {
                        System.out.println("ℹ️ 등록된 회원이 없습니다.");
                    } else {
                        System.out.println("--- 📋 전체 회원 목록 📋 ---");
                        for (Member member : allMembers) {
                            System.out.println("👤 ID=" + member.getId() + ", 이름=" + member.getName());
                        }
                        System.out.println("--------------------------");
                    }
                }

                case "4" -> ExceptionHandler.execute(() -> {
                    System.out.print("삭제할 회원 ID를 입력하세요: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    memberController.deleteMember(id);
                    System.out.println("✅ 회원 삭제 완료 (ID: " + id + ")");
                });

                case "5" -> {
                    System.out.println("👋 서비스를 종료합니다. 안녕히 계세요!");
                    scanner.close();
                    return;
                }

                default -> System.out.println("🚫 잘못된 메뉴 선택입니다. 다시 시도해주세요.");
            }
        }
    }
}