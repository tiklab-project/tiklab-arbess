package net.tiklab.matflow.execute.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AAAAA {

    public static void main(String[] args) throws IOException {
        String fileName = "C:\\Users\\admin\\.tiklab\\matflow\\logs\\0a5444d7d830fc92914751fc9d626341\\17c9b192664752f6945bdea6b4f80c19\\c01acf55cefa0936902afd71bb276865.log";

        File file = new File(fileName);
        if (!file.exists()){
            System.out.println("文件不存在。");
            return;
        }
        // LongAdder longAdder = new LongAdder();
        // // 读取文件内容到Stream流中，按行读取
        // try {

        //
        //     // Stream<String> lines = Files.lines(path);
        //     // String line32 = Files.readAllLines(Paths.get("file.txt")).get(32);
        //     // long size = Files.size(path);
        Path path = Paths.get(fileName);
        String s = "";
        List<String> lines = Files.readAllLines(path);
        for (int i = Math.max(0, lines.size() - 3); i < lines.size(); i++) {
            s = s + lines.get(i);
            System.out.println(lines.get(i));
        }
        //
        //     // System.out.println(size);
        //     // lines.forEach(s -> {
        //     //
        //     // });
        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }

        // File file = new File("D:\\file_name.xml");
        // int n_lines = 5;
        // int counter = 0;
        // ReversedLinesFileReader object = new ReversedLinesFileReader(file);
        // while(counter < n_lines) {
        //     System.out.println(object.readLine());
        //     counter++;
        // }


        // List content=readLastLine(fileName);
        // System.out.println( content);
    }

    /**
    //  * 读取日志最后N行
    //  * @param path
    //  * @return
    //  */
    // public static String readRunLog(String path) {
    //     File file = new File(path);
    //     String result = null;
    //     try (ReversedLinesFileReader reader = new ReversedLinesFileReader(file)) {
    //
    //         String line = "";
    //         while ((line = reader.readLine()) != null && result.size() < 100) {
    //             result.add(line);
    //         }
    //         //倒叙遍历
    //         Collections.reverse(result);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return result;
    // }


}
