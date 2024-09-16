package com.mahait.gov.in.controller.datamigration;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class BlobToPhysicalFileConverterServiceImpl implements BlobToPhysicalFileConverterService {

	@Autowired
	BlobToPhysicalFileConverterRepo blobToPhysicalFileConverterRepo;
	
	@Autowired
	private Environment environment;
	

	@Override
	public void convertBlogToFile(Integer typeOp) {
		List<Object[]> results = blobToPhysicalFileConverterRepo.convertBlogToFile(typeOp);

		Tika tika = new Tika();
		for (Object[] row : results) {
			String fileName = row[0].toString();
			byte[] byteData = (byte[]) row[1];
		//	byte[] byteData = (byte[]) row[2];
			
			
			List<byte[]> lst=new ArrayList<>();

			try {
				String fileExtension = getFileExtension(byteData, tika);
				String fullFileName = fileName + "." + fileExtension;

				try (OutputStream outputStream = new FileOutputStream(fullFileName)) {
					outputStream.write(byteData);
				}

				System.out.println("Data has been written to " + fullFileName);

			} catch (IOException e) {
				System.err.println("Error writing file: " + e.getMessage());
			}
		}
	}

	private String getFileExtension(byte[] byteData, Tika tika) {
		try {
			String mimeType = tika.detect(byteData);
			String[] parts = mimeType.split("/");
			return parts.length > 1 ? parts[1] : "bin";
		} catch (Exception e) {
			System.err.println("Error detecting file type: " + e.getMessage());
			return "bin"; // Fallback extension
		}
	}
	
	
	public String saveAttachment(MultipartFile[] files, Integer saveIdnew, Long long1,Integer fileNo) {
		String res =null;
		if (files.length != 0) {
			int width = 963;
			int height = 640;
			try {
				byte[] bytes = files[fileNo].getBytes();
				String extension=StringUtils.getFilenameExtension(files[fileNo].getOriginalFilename());
				if (bytes.length != 0) {
					BufferedImage image = null;
					File f = null;
					InputStream in = new ByteArrayInputStream(bytes);
					image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					image = ImageIO.read(in);
				
					String key = "";
					String rootPath = "";
					String strOSName = System.getProperty("os.name");
					boolean test = strOSName.contains("Windows");
					if (strOSName.contains("Windows")) {
						key = "serverempconfigimagepath";
					} else {
						key = "serverempconfigimagepathLinuxOS";
					}
					rootPath = environment.getRequiredProperty(key);
					rootPath += saveIdnew.toString() + File.separator + long1;
					File dir = new File(rootPath);
					if (!dir.exists())
						dir.mkdirs();
					
					Date date=new Date();

					String name = files[fileNo].getOriginalFilename();
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					res = dir.getAbsolutePath() + File.separator + name;

				} else {
					res = "";
				}
			} catch (Exception e) {
				e.printStackTrace();
				res = "";
			}
		}
		return res;
	}

	@Override
	public String[] savePhotoSignature(MultipartFile[] files, String DeptNm, Long empid) {
		// department name/photo/employee_id/photo.jpg
		String[] res = new String[2];
		if (files.length != 0) {
			int width = 963;
			int height = 640;

			try {
				byte[] bytes = files[0].getBytes();
				if (bytes.length != 0) {
					BufferedImage image = null;
					File f = null;
					InputStream in = new ByteArrayInputStream(bytes);
					image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					image = ImageIO.read(in);
					String key = "";
					String rootPath = "";
					String strOSName = System.getProperty("os.name");
					boolean test = strOSName.contains("Windows");
					if (strOSName.contains("Windows")) {
						key = "serverempconfigimagepath";
					} else {
						key = "serverempconfigimagepathLinuxOS";
					}
					rootPath = environment.getRequiredProperty(key);
					rootPath += DeptNm + File.separator + empid;
					File dir = new File(rootPath);
					if (!dir.exists())
						dir.mkdirs();

					String name = "photo.jpg";
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					res[0] = dir.getAbsolutePath() + File.separator + name;

				} else {
					res[0] = "";
				}
			} catch (Exception e) {
				e.printStackTrace();
				res[0] = "";
			}
		}

		// signature image code started
		if (files.length != 0) {
			try {
				byte[] bytes = files[1].getBytes();
				boolean var = bytes.length != 0;

				if (bytes.length != 0) {
					BufferedImage image = null;
					File f = null;
					int width = 963;
					int height = 640;
					InputStream in = new ByteArrayInputStream(bytes);
					image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					image = ImageIO.read(in);
					String key = "";
					String rootPath = "";
					String strOSName = System.getProperty("os.name");
					boolean test = strOSName.contains("Windows");
					if (strOSName.contains("Windows")) {
						key = "serverempconfigimagepath";
					} else {
						key = "serverempconfigimagepathLinuxOS";
					}
					rootPath = environment.getRequiredProperty(key);
					rootPath += DeptNm + File.separator + empid;
					// String rootPath ="C:\\Users\\jjman\\OneDrive\\Pictures\\server";
					File dir = new File(rootPath);
					if (!dir.exists())
						dir.mkdirs();
					String name = "signature.jpg";
					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					res[1] = dir.getAbsolutePath() + File.separator + name;
				} else {
					res[1] = "";
				}
			} catch (Exception e) {
				e.printStackTrace();
				res[1] = "";
			}
		}
		// signature code ended
		return res;
	}

}
