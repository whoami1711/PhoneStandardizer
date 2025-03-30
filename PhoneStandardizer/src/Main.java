import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        try {
            // read file input.txt
            String content = new String(Files.readAllBytes(Paths.get("input.txt")));
            System.out.println("Начало содержание:");
            System.out.println(content);

            // Chuẩn hóa số điện thoại
            String updatedContent = standardizePhoneNumbers(content);
            System.out.println("\nСодержание после стандартизованное:");
            System.out.println(updatedContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // функция стандарт
    public static String standardizePhoneNumbers(String text) {
        // Regex linh hoạt để bắt các số điện thoại kiểu +7 với các khoảng trắng, dấu ngoặc hoặc dấu gạch tùy chọn.
        // Cú pháp giải thích:
        // \+7           : bắt đầu bằng +7
        // \s*           : 0 hoặc nhiều khoảng trắng
        // \(?\s*        : tùy chọn mở ngoặc ( và khoảng trắng
        // (\d{3})       : nhóm 1: 3 chữ số (mã vùng)
        // \s*\)?\s*     : tùy chọn đóng ngoặc ) và khoảng trắng
        // [-]?\s*       : tùy chọn dấu gạch và khoảng trắng
        // (\d{3})       : nhóm 2: 3 chữ số
        // [-]?\s*       : tùy chọn dấu gạch và khoảng trắng
        // (\d{2})       : nhóm 3: 2 chữ số
        // [-]?\s*       : tùy chọn dấu gạch và khoảng trắng
        // (\d{2})       : nhóm 4: 2 chữ số cuối
        String regex = "\\+7[-\\s]*\\(?[-\\s]*(\\d{3})[-\\s]*\\)?[-\\s]*(\\d{3})[-\\s]*(\\d{2})[-\\s]*(\\d{2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        // использование StringBuffer чтобы построение новый содержание
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            // Định dạng lại theo yêu cầu: đổi +7 thành +1 và định dạng: +1 (group1) group2-group3-group4
            // Nếu cần sử dụng ký tự "¬" thay cho dấu gạch ngang, thay "-" bằng "¬" ở đây.
            String replacement = "+1 (" + matcher.group(1) + ") " + matcher.group(2) + "-" + matcher.group(3) + "-" + matcher.group(4);
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
