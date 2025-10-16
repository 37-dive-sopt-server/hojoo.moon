package org.sopt.member.repository;

import org.sopt.member.domain.Gender;
import org.sopt.member.domain.Member;
import org.sopt.util.exception.StorageException;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static org.sopt.util.exception.ErrorCode.*;

public class FileMemberRepository implements MemberRepository {

    private static final int FIELD_SIZE = 5;
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
            skipHeader(reader);

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                Member member = parseLine(line);
                save(member);
                updateSequence(member.getId());
            }
        } catch (IOException e) {
            throw new StorageException(STORAGE_LOAD_FAILED);
        }
    }

    private void skipHeader(BufferedReader reader) throws IOException {
        reader.readLine();
    }

    private Member parseLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != FIELD_SIZE) {
            throw new StorageException(STORAGE_INVALID_FORMAT);
        }

        try {
            Long id = Long.parseLong(parts[0]);
            String name = parts[1];
            LocalDate birthDate = LocalDate.parse(parts[2]);
            String email = parts[3];
            Gender gender = Gender.valueOf(parts[4]);

            return Member.create(id, name, birthDate, email, gender);
        } catch (RuntimeException e) {
            throw new StorageException(STORAGE_PARSE_FAILED, e);
        }
    }

    private void updateSequence(Long id) {
        if (id >= sequence) {
            sequence = id + 1;
        }
    }

    @Override
    public void saveToFile() {
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
            throw new StorageException(STORAGE_SAVE_FAILED);
        }
    }

    @Override
    public Member save(Member member) {
        store.put(member.getId(), member);
//        saveToFile();
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
    public boolean existsByEmail(String email) {
        return store.values().stream()
                .anyMatch(member -> member.getEmail().equals(email));
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
//        saveToFile();
    }
}
