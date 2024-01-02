package ru.gameengine.Web;

import net.minecraft.client.Minecraft;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class HTMLUtils {
    public static String load(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc.html();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String downloadZip(String url) throws Exception {
        // Путь к папке, в которую нужно распаковать архив
        String extractPath = Minecraft.getInstance().gameDirectory.getAbsolutePath() + "/saves";

        // Скачиваем архив
        URL zipUrl = new URL(url);
        InputStream in = zipUrl.openStream();
        File zipFile = new File("world.zip");
        FileOutputStream out = new FileOutputStream(zipFile);
        IOUtils.copy(in, out);
        IOUtils.closeQuietly(in);
        IOUtils.closeQuietly(out);

        // Распаковываем архив в указанную папку
        ZipInputStream zipIn = new ZipInputStream(FileUtils.openInputStream(zipFile));
        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
            File file = new File(extractPath, entry.getName());
            if (!entry.isDirectory()) {
                FileUtils.copyInputStreamToFile(zipIn, file);
            } else {
                file.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
        zipFile.delete();
        return "Загружено!";
    }
}