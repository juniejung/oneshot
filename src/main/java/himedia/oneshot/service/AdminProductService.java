package himedia.oneshot.service;

import himedia.oneshot.entity.Product;
import himedia.oneshot.repository.JdbcAdminProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdminProductService {

    private final JdbcAdminProductRepository adminProductRepository;

    public void saveImage(File savePath, MultipartFile imageFile) throws Exception {
//        UUID uuid = UUID.randomUUID();
//        String stringUuid = uuid.toString().replaceAll("-","");
        String imageName = imageFile.getOriginalFilename();
        File saveFile = new File(savePath, imageName);
        imageFile.transferTo(saveFile);
    }

    public void saveProduct(Product product, MultipartFile thumbImgFile,MultipartFile[] expImgFiles)throws Exception{

        String thumbPath = System.getProperty("user.dir") +
                "/src/main/resources/static/img/product/thumbnail";
        String expPath = System.getProperty("user.dir")+
                "/src/main/resources/static/img/product/explanation";

        saveImage(new File(thumbPath), thumbImgFile);
        log.info("썸네일 저장완료");

        List<String> expImgNames = new ArrayList<>();
        for (MultipartFile imgFile : expImgFiles) {
            saveImage(new File(expPath), imgFile);
            expImgNames.add(imgFile.getOriginalFilename());
        }

        for (int i = 0; i < expImgFiles.length; i++){
            if (i < expImgNames.size() && !expImgNames.get(i).isEmpty()) {
                File expSaveFile = new File(expPath, expImgNames.get(i));
                expImgFiles[i].transferTo(expSaveFile);
            }
        }

        log.info("설명저장완료");
        product.setImg_thumb("img/product/thumbnail/" + thumbImgFile.getOriginalFilename());
        product.setImg_exp1("img/product/explanation/" + expImgNames.get(0));
        if (expImgNames.size() > 1 && !expImgNames.get(1).isEmpty()) {
            product.setImg_exp2("img/product/explanation/" + expImgNames.get(1));
        }
        if (expImgNames.size() > 2 && !expImgNames.get(2).isEmpty()) {
            product.setImg_exp3("img/product/explanation/" + expImgNames.get(2));
        }

        adminProductRepository.saveProduct(product);
    }
    public void updateProduct(Long id,Product updatedProduct,MultipartFile thumbImgFile, MultipartFile[] expImgFiles) throws Exception {

        String thumbPath = System.getProperty("user.dir")
                + "/src/main/resources/static/img/product/thumbnail";
        String expPath = System.getProperty("user.dir")
                + "/src/main/resources/static/img/product/explanation";

        saveImage(new File(thumbPath), thumbImgFile);
        log.info("썸네일 저장완료");

        List<String> expImgNames = new ArrayList<>();
        for (MultipartFile img : expImgFiles) {
            saveImage(new File(expPath), img);
            expImgNames.add(img.getOriginalFilename());
        }

        for (int i = 0; i < expImgFiles.length; i++){
            if (i < expImgNames.size() && !expImgNames.get(i).isEmpty()) {
                File expSaveFile = new File(expPath, expImgNames.get(i));
                expImgFiles[i].transferTo(expSaveFile);
            }
        }
        log.info("설명저장완료");
        updatedProduct.setImg_thumb("img/product/thumbnail/" + thumbImgFile.getOriginalFilename());
        updatedProduct.setImg_exp1("img/product/explanation/" + expImgNames.get(0));
        if (expImgNames.size() > 1 && !expImgNames.get(1).isEmpty()) {
            updatedProduct.setImg_exp2("img/product/explanation/" + expImgNames.get(1));
        }
        if (expImgNames.size() > 2 && !expImgNames.get(2).isEmpty()) {
            updatedProduct.setImg_exp3("img/product/explanation/" + expImgNames.get(2));
        }

        adminProductRepository.updateProduct(id, updatedProduct);
    }

    public void deleteProduct(Long id){
        adminProductRepository.deleteProduct(id);
    }

    public Optional<Product> findById(Long id){
        return adminProductRepository.findById(id);
    }

    public List<Product> findAll(){
        return  adminProductRepository.findAll();
    }


}
