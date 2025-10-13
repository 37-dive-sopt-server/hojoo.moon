package org.sopt.repository;

import org.sopt.domain.Gender;
import org.sopt.domain.Member;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class FileMemberRepository implements MemberRepository {

    private final Map<Long, Member> store = new HashMap<>();
    private long sequence = 1L;
    private final String filePath;

    public FileMemberRepository(String filePath) {
        this.filePath = filePath;
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Long id = Long.parseLong(parts[0]);
                    String name = parts[1];
                    LocalDate birthDate = LocalDate.parse(parts[2]);
                    String email = parts[3];
                    Gender gender = Gender.valueOf(parts[4]);

                    Member member = new Member(id, name, birthDate, email, gender);
                    store.put(id, member);

                    if (id >= sequence) {
                        sequence = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("id,name,birthDate,email,gender");
            writer.newLine();

            for (Member member : store.values()) {
                writer.write(String.format("%d,%s,%s,%s,%s",
                        member.id(),
                        member.name(),
                        member.birthDate(),
                        member.email(),
                        member.gender()));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public Member save(Member member) {
        store.put(member.id(), member);
        saveToFile();
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return store.values().stream()
                .filter(member -> member.email().equals(email))
                .findFirst();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Long generateNextId() {
        return sequence++;
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
        saveToFile();
    }
}