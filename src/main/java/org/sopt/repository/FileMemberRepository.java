package org.sopt.repository;

import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.exception.StorageException;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileMemberRepository implements MemberRepository {

    private final Map<Long, Member> store = new HashMap<>();
    private Long sequence = 1L;
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

                    Member member = Member.create(id, name, birthDate, email, gender);
                    store.put(id, member);

                    if (id >= sequence) {
                        sequence = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            throw new StorageException("회원 데이터 파일 로드 실패");
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("id,name,birthDate,email,gender");
            writer.newLine();

            for (Member member : store.values()) {
                writer.write(String.format("%d,%s,%s,%s,%s",
                        member.getId(),
                        member.getName(),
                        member.getBirthDate(),
                        member.getEmail(),
                        member.getGender()));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new StorageException("회원 데이터 파일 저장 실패");
        }
    }

    @Override
    public Member save(Member member) {
        store.put(member.getId(), member);
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
                .filter(member -> member.getEmail().equals(email))
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