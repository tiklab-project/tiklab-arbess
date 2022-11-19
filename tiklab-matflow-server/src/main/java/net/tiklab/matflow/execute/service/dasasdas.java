package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.orther.service.PipelineUntil;

import java.io.*;

public class dasasdas {

    public static void main(String[] args) throws IOException {
        String tempFile = createTempFile(key);
        String userHome = System.getProperty("user.home");
        System.out.println(userHome);
        if (!PipelineUntil.isNoNull(tempFile)){
            return;
        }
        String address = tempFile.replace(userHome, "");
        String addresss = address.replace("\\", "/");
        System.out.println("地址：~"+addresss);
        String order = ".\\git.exe config --global core.sshCommand \"ssh -i ~" + addresss + "\"";
        String ordera = ".\\git.exe git config --global --unset core.sshCommand";
        String orders = ".\\git.exe clone git@gitee.com:zcamy/car.git D:\\clone\\zeze";
        System.out.println(ordera+";"+order+";"+orders);
        Process process = process("D:\\git\\Git\\bin", ordera+";"+order+";"+orders);

        log(process.getInputStream(),process.getErrorStream(),null);

        PipelineUntil.deleteFile(new File(tempFile));
    }

    public static String createTempFile(String key){
        File tempFile;
        String path;
        try {
            tempFile = File.createTempFile("key", ".txt");
            path = tempFile.getPath();
            FileWriter writer;
            writer = new FileWriter(path);
            writer.write(key);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            return null;
        }
        return path;
    }

    public static Process process(String path,String order) throws IOException {
        Runtime runtime=Runtime.getRuntime();
        Process process;
        if (PipelineUntil.findSystemType()==1){
            process = runtime.exec(" cmd.exe /c cd " + path + " &&" + " " + order);
        }else {
            String[] cmd = new String[] { "/bin/sh", "-c", "cd "+path+";"+" source /etc/profile;"+ order };
            process = runtime.exec(cmd);
        }
        return process;
    }

    public static int log(InputStream inputStream, InputStream errInputStream,String encode) throws IOException {
        int state = 1;

        InputStreamReader inputStreamReader = PipelineUntil.encode(inputStream, encode);

        String s;
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder logRunLog = new StringBuilder();

        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            logRunLog.append(s).append("\n");
            if (logRunLog.toString().contains("BUILD FAILURE")) {
                System.out.println(s);
            }

        }
        if (!PipelineUntil.isNoNull(logRunLog.toString())){
            inputStreamReader = PipelineUntil.encode(errInputStream, encode);
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((s = bufferedReader.readLine()) != null) {
                System.out.println(s);
            }
        }
        inputStreamReader.close();
        bufferedReader.close();
        return state;
    }

    static String  key = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIG4gIBAAKCAYEAxuyDKkHzr9BN5dl6EmEgIBSWnyPXWCG6H2IUMj4vILfCQqA3\n" +
            "j3WQ3Xa3sJnffTV9drG6miZTdcuc1cp+attVwNPsqdQEZmmiwNJ7YjzUx9w4glTD\n" +
            "ONvSJ6+B9KKg+sZUSh1VNd7kOoAYzJxSA4rz5BT/xivCoZIJiYG3lqxep6biL1sX\n" +
            "2eWxiWrQANVvDWwN3kbeJxO52I1nBMnho/2K1mOGwk/l6yQ5eDXpBt3LH2muzMz7\n" +
            "YsEjT7iKRGqa5p6w4KZhe3t4uqVSfO/ALUq+2gkgv86SHJJhnvdQAorFxlb9f3Jp\n" +
            "dCEa5gPqlx81ZIzDpjsqXb1KL/NXR00QSL6k9XDnHlI7LVXQBn4F1dVy65kJwdud\n" +
            "VhXkwmdB5ZuBKpA/JnbsnnEJkO33BnV2OPh8c+GIwNKaraZvt4TOxYqWjpadj6MJ\n" +
            "55k8AHTHiUbFEUkZEEyRmh8uxZh/Y+8yA/lOdgGEd5+4qZ021ror23zq6u2WhUXA\n" +
            "aOJcTOi1QmIqdZWFAgMBAAECggGAGzbkQvW+5wCyh42XO8h54qmuaZs3rOEAW6bJ\n" +
            "aI5FWE6Ljx3oPAIzjXjebJTAlJqqzYBHLSutI2J11HHdlbToKHdV224cI/6zu3mQ\n" +
            "U8JKwgUI3hvGkZLRT4idWOCe8XAMvA8DR1l1E+POgL+qIL3z2/Jbg7sc54nZMCzE\n" +
            "9yIh2QHl0+C8hJz+CvTH/DU2+jkcNJqxbdsvA8dknq0X552aTvZSy2au59qO2jlV\n" +
            "5hCV53hH59DrW3lc4V033WBQTNjM0A7tUi/ziilmNsPjWvWJAlu4WCvQ0C1H3se0\n" +
            "3rU6GK//up+tHPK8Y4NSmTgGMQelt6dAdg21vEfwPYCgT/hW6iB+u0BQBgm/CUnz\n" +
            "/utEm+ZfjqU3aEik9K7Vj9x2mEdQF+Q7uQiVo5SsP9ReW/gKR1lvWh2IVIo39Wyu\n" +
            "wTsVu+I+EHGHl9DvuXbFZ+rj5VnYsknZhv1KDQgO1h40jGtQcWMTGduZxgysBNx+\n" +
            "ZgIznx/jn3SNEMFpbcpeWUbJzJcBAoHBAPcbsQigRa0d8FRXteaxFYYapTdfJUMP\n" +
            "htoz8NCOhTaLYlILbGKwEiyoIjR4re+FY07dRESyvGf+OMZ/IO75nxNpIEe9Vo3y\n" +
            "nDyW2kkZcs6XHzYopD7FlOukro944b18eyBMv6K4baXVxfBwlIA3utYi00dEb1T5\n" +
            "n10d2cA4/7XyyogY+ZQ/NzSKPnkDp1muaMMBb65oSSa4a3oFNRvVpGvup0JK7j7D\n" +
            "cldhA9Qm2fuUDFAIiv5xhxAjnCOKiYgsoQKBwQDOFPUOfLL2xj6xIM1gySxi0Kdk\n" +
            "dGwbhXrjYiH3nyYRTk6GcgRqs0tbqHiYQQOSjnbq5b9X9zOOWSbMnVge4Esx0ofb\n" +
            "621gzrMaOJCK/DMTCxGE4a7ejN1OZnVzbd+lyOQVHejDorgCU8G3uMGBSv/grgOp\n" +
            "9G5T+g/rtxHcb7aC+9Lz6CvPxHmQZiReUNOWqYjWuoyC2NgxHfujpUWj5zkQ/0Nm\n" +
            "IrhwfcUjkQabrUaEsTsxovRHeqZR9iWi8y3vumUCgcBW94b5yzZadBP2Cxx4wVAa\n" +
            "71vAPhsmG9vRBcC2FcKqbKnAyEZwARmUX+QR5XsJtj86LUNujh0i2GTw7yMPGAk1\n" +
            "UqVKukcxE/aKmGjgmzU9ohAZBnqvoOgFeuL4mYmRH7NRrdEwGop0d+T+9xA8g+Cc\n" +
            "posHJP2twWCl6/CL50OPrJA9D9UeqJTsR88u1eta87fpEaNVlVxRZUxmGFkVn74L\n" +
            "QmXZPffVXQHr4LfxPW8wTG186VTJsfjf5Dh6hbJbm6ECgcA9f0mFsjahp5UrvHMt\n" +
            "T7s8HbHmK0vbhqCeXDlwsIBmp6yNOFcTFEAHsSWTocsUIAlinX7VHEK5VudaJhYW\n" +
            "2K33xzCdswqn2ebei0SvBQWkyCfJNKO/a9/atn6UHJSh32oY1x0PzG4G28GyNqMg\n" +
            "FyZ2gNc9D2EhFD1OBQqCcp/PRKC2AnGYhbQVIDX4IzyQ+DSKh47IuTcCqUTfl8FJ\n" +
            "YQXJibMuNmw9vXpn265SJIonrM/SRyOIdRRO3xpqHxF1OXUCgcA2JbYK5A84NB/+\n" +
            "OKfOojPAAbB9FVZSNU3w3nh8/vKrc3kdH3ITYShqVeENB7gIqRRRj+y1cVr1hwIi\n" +
            "d7ijix1a7d7PoXCBh4xNOeRo2rqAjxK1GTyQrfACV8GPyFI4KHX5rVIPvmaS/m36\n" +
            "1ftjhkjoIz/LWCv75F+AC1frmMFixNtcCpZrw0H02BZsPMWOdqZnRar3ZGZ0VXau\n" +
            "NwNAOHdUwzxH16Otgah0uMSUtUC4Awkkxyc2TueDFagISWxrI60=\n" +
            "-----END RSA PRIVATE KEY-----\n";


}
