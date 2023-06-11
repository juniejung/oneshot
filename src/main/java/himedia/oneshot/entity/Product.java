package himedia.oneshot.entity;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;             //상품 고유 번호
    private String name;        //상품 이름
    private int quantity;       //상품수량
    private String type_local;   //지역
    private String type_kind;    //주종
    private String creator;     //제조사
    private float alcohol;      // 도수
    private int price;          //상품 가격
    private String img_thumb;    //상품 썸네일 이미지 파일 경로
    private String img_exp1;     //상품 상세 내용 이미지 파일 경로1
    private String img_exp2;     //상품 상세 내용 이미지 파일 경로2
    private String img_exp3;     //상품 상세 내용 이미지 파일 경로3
    public Product(String name, int quantity,String type_local,String type_kind, String creator, float alcohol, int price, String img_thumb, String img_exp1, String img_exp2, String img_exp3) {
        this.name = name;
        this.quantity = quantity;
        this.type_local = type_local;
        this.type_kind = type_kind;
        this.creator = creator;
        this.alcohol = alcohol;
        this.price = price;
        this.img_thumb = img_thumb;
        this.img_exp1 = img_exp1;
        this.img_exp2 = img_exp2;
        this.img_exp3 = img_exp3;
    }
}
