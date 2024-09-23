package com.mahait.gov.in.controller.datamigration;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.GROrderDocumentEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;


@Transactional
@Service
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
			BigInteger id = StringHelperUtils.isNullBigInteger(row[2]);

			List<byte[]> lst = new ArrayList<>();
			String fileExtension = getFileExtension(byteData, tika);
			String fullFileName = fileName + "." + fileExtension;
			String DeptNm = "";

			if (typeOp == 1) {
				String filePath = uploadPhoto(byteData, DeptNm, id);
				if (filePath != null) {

					MstEmployeeEntity mstEmployeeEntity = blobToPhysicalFileConverterRepo.findEmpByEmpId(id);
					//mstEmployeeEntity.setPhotoAttachmentId(filePath);
					//mstEmployeeEntity.setEmployeeId(id.longValue());
					//blobToPhysicalFileConverterRepo.updatePhotoPath(mstEmployeeEntity);
				}

			} else if (typeOp == 2) {
				String filePath = uploadSignature(byteData, DeptNm, id);
				if (filePath != null) {
					MstEmployeeEntity mstEmployeeEntity = blobToPhysicalFileConverterRepo.findEmpByEmpId(id);
					mstEmployeeEntity.setSignatureAttachmentId(filePath);
					blobToPhysicalFileConverterRepo.updatePhotoPath(mstEmployeeEntity);
				}
			} else {
				
				String filePath = saveAttachment(byteData, id, fileExtension);
				if (filePath != null) {
					BigInteger grDocumentId = StringHelperUtils.isNullBigInteger(row[3]);
					GROrderDocumentEntity gROrderDocumentEntity =blobToPhysicalFileConverterRepo.findDocumentId(grDocumentId);
					gROrderDocumentEntity.setFilePath(filePath);
					blobToPhysicalFileConverterRepo.updateGrPath(gROrderDocumentEntity);
				}

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

	public String saveAttachment(byte files[], BigInteger grOrderId, String fileExtension) {
		String res = null;
		if (files.length != 0) {
			int width = 963;
			int height = 640;
			try {
				byte[] bytes = files;
				String extension = fileExtension;
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
						key = "serverGrOrderpath";
					} else {
						key = "serverGrOrderLinuxOS";
					}
					rootPath = environment.getRequiredProperty(key);
					rootPath += grOrderId.toString();
					File dir = new File(rootPath);
					if (!dir.exists())
						dir.mkdirs();

					Date date = new Date();
					String name = grOrderId + "." + fileExtension;
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

	/*@Override
	public String uploadPhoto(byte files[], String DeptNm, BigInteger empid) {
		String res = null;
		if (files.length != 0) {
			int width = 963;
			int height = 640;

			try {
				byte[] bytes = files;
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

	}*/

	
	@Override
	public String uploadPhoto(byte[] files, String DeptNm, BigInteger empid) {
	    String res = null;
	    if (files != null && files.length > 0) {
	        try {
	            // Create a buffered image from byte array
	            InputStream in = new ByteArrayInputStream(files);
	            BufferedImage image = ImageIO.read(in);
	            
	            if (image == null) {
	                // If ImageIO couldn't read the image, return an error message
	                res = "Invalid image data.";
	                return res;
	            }

	            // Determine the correct file path
	            String key = "";
	            String rootPath = "";
	            String strOSName = System.getProperty("os.name");
	            if (strOSName.contains("Windows")) {
	                key = "serverempconfigimagepath";
	            } else {
	                key = "serverempconfigimagepathLinuxOS";
	            }
	            rootPath = environment.getRequiredProperty(key);
	            rootPath += DeptNm + File.separator + empid;
	            File dir = new File(rootPath);
	            if (!dir.exists()) {
	                dir.mkdirs(); // Create directories if they do not exist
	            }

	            // Define file name and write the image to the file
	            String name = "photo.jpg";
	            File serverFile = new File(dir.getAbsolutePath() + File.separator + name);

	            // Write the image to file
	            try (OutputStream os = new BufferedOutputStream(new FileOutputStream(serverFile))) {
	                ImageIO.write(image, "jpg", os); // Write the buffered image to file
	            }

	            res = serverFile.getAbsolutePath(); // Return the path to the saved file
	        } catch (IOException e) {
	            e.printStackTrace();
	            res = "Error writing file.";
	        }
	    } else {
	        res = "No file data provided.";
	    }
	    return res;
	}

	@Override
	public String uploadSignature(byte files[], String DeptNm, BigInteger empid) {
		// signature image code started
		String res = null;
		if (files.length != 0) {
			try {
				byte[] bytes = files;
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

					res = dir.getAbsolutePath() + File.separator + name;
				} else {
					res = "";
				}
			} catch (Exception e) {
				e.printStackTrace();
				res = "";
			}
		}
		// signature code ended
		return res;
	}

}
