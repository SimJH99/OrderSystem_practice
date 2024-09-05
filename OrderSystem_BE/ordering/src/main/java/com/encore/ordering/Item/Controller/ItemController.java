package com.encore.ordering.Item.Controller;

import com.encore.ordering.Common.CommonResponse;
import com.encore.ordering.Item.Domain.Item;
import com.encore.ordering.Item.Dto.ItemReqDto;
import com.encore.ordering.Item.Dto.ItemResDto;
import com.encore.ordering.Item.Dto.ItemSearchDto;
import com.encore.ordering.Item.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    //상품 등록
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/item/create")
    public ResponseEntity<CommonResponse> itemCreate(ItemReqDto itemReqDto){
        Item item = itemService.create(itemReqDto);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED, "item succesfully create", item.getId()), HttpStatus.CREATED);
    }

    //상품 목록 조회
    @GetMapping("/items")
    public  ResponseEntity<List<ItemResDto> > itemList(ItemSearchDto itemSearchDto, Pageable pageable){
        List<ItemResDto> itemResDtos = itemService.findALl(itemSearchDto, pageable);
        return new ResponseEntity<>(itemResDtos, HttpStatus.OK);
    }

    @GetMapping("/")
    public String home(){
        return "OK";
    }

    //현재는 publicStorage가 없어서 컴퓨터 로컬경로로 지정해서 imageUrl을 불러오면 frontend에서 로컬경로로 찾을 수 없어 error가 발생하기 때문에
    // 현재는 Resource 형태로 가져와서 이미지를 제공해주는 방식으로 이미지를 전달해준다.
    @GetMapping("/item/{id}/image")
    public ResponseEntity<Resource> getImage(@PathVariable Long id){
        Resource resource = itemService.getImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @DeleteMapping("/item/{id}/delete")
    public ResponseEntity<CommonResponse> itemDelete(@PathVariable Long id){
        Item item = itemService.delete(id);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK, "item succesfully delete", item.getId()), HttpStatus.OK);
    }

}
