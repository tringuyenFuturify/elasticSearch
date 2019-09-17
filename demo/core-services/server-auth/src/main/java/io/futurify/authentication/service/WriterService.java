package io.futurify.authentication.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class WriterService {
  
  protected Session session;
  
  public void writer() {
    List<String> str =
        Arrays.asList(new String[] {"Nguyen Thanh Tri", "22/09/1995", "+84368904438", "tp hcm"});
    List<String> headers = Arrays.asList(new String[] {"A", "B", "C", "D"});
    List<String> aliases = Arrays.asList(new String[] {"col 1", "col 2", "col 3", "col 4"});
    byte[] content = null;
    try {
      try (final StringWriter writer = new StringWriter()) {
        try (final CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
            .withDelimiter(',').withHeader(aliases.toArray(new String[aliases.size()])))) {
          for (Object record : str) {
            csvPrinter.printRecord(extractValues(headers.toArray(new String[headers.size()])));
          }
          csvPrinter.flush();
        }
        StringBuffer buffer = writer.getBuffer();
        content = buffer.toString().getBytes();
      }
      File file = new File("asd.csv");
      OutputStream os = new FileOutputStream(file);
      os.write(content);
      os.close();
     
      file.delete();

    } catch (IOException e) {
      log.error("Write CSV error ", e);
    } catch (Exception e) {
      log.error("Write file error ", e);
    }
  }
  
  public String connectAndExecute(String user, String host, String password) {
    String CommandOutput = null;
    try {

      JSch jSch = new JSch();
      session = jSch.getSession(user, host, 22);
      session.setPassword(password);
      session.setConfig("StrictHostKeyChecking", "no");
      session.connect();
      
      Channel channel = session.openChannel("sftp");
      channel.connect();
      ChannelSftp sftpChannel = (ChannelSftp) channel;
      String path = "C:/ProgramData/ssh/acxs.csv";
      byte[] buff = path.getBytes();

      File file = new File("asd.txt");
      OutputStream os = new FileOutputStream(file);
      os.write(buff);
      os.close();
      
      sftpChannel.put(new FileInputStream(file), "./acxs.txt");
      
      sftpChannel.exit();
      
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
      session.disconnect();
    }
    return CommandOutput;

}

  private Object[] extractValues(String[] headers) {
    Object[] values = new Object[headers.length];
    List<String> str =
        Arrays.asList(new String[] {"Nguyen Thanh Tri", "22/09/1995", "+84368904438", "tp hcm"});
    for (int i = 0; i < headers.length; i++) {
      values[i] = str.get(i);
    }

    return values;
  }

}
