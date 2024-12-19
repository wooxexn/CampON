package com.tz.campon.board.post;

import lombok.Getter;

@Getter
public class PageHandler {

    int currentPage; //현재 페이지
    int totRecord; //총 레코드
    int pageSize; //한 페이지에 보이는 수
    int totalPage; //총 페이지 수

    int grpSize;
    int currentGrp;
    int grpStartPage;
    int grpEndPage;

    public PageHandler(int page, int totRecords, int pageSize, int grpSize){
       this.currentPage = page;
       this.totRecord = totRecords;
       this.pageSize = pageSize;
       this.grpSize = grpSize;
    }

    private void calcPage(){
        int remain = totRecord % pageSize;
        //총 페이지 수
        if (remain == 0)
            totalPage = totRecord / pageSize;
        else
            totalPage = totRecord / pageSize + 1;

        int remain2 = currentPage % grpSize;

        if (remain2 == 0)
            currentGrp = currentPage / grpSize;
        else currentGrp = currentPage / grpSize + 1;

        grpStartPage = (currentGrp - 1) * grpSize + 1;
        grpEndPage = currentGrp * grpSize;

        if(grpEndPage > totalPage){
            grpEndPage = totalPage;
        }
    }

}
