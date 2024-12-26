package com.tz.campon.reservation.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageHandler {

    int  currentPage;          //현재페이지
    int  totRecords ;          // 총 레코드 수
    int  pageSize ;              //페이지 사이즈  (데이터 건수 , 한 페이지에 보이는 레코드 수 )
    int  totalPage;             // 총 페이지수: 전체 페이수

    int  grpSize ;             // 한 그룹에 5깨씩 보겟다
    int  currentGrp ;  	      // 현재그룹
    int  grpStartPage;           // 현재그룹의 시작번호
    int  grpEndPage;             // 현재그룹의 마지막번호


    public PageHandler(int page, int totRecords , int pageSize , int grpSize) {
        this.currentPage= page;
        this.totRecords= totRecords;
        this.pageSize = pageSize;
        this.grpSize = grpSize;


        //페이징관련 값 구하기
        calcPage();
    }


    private void calcPage(){
        int reamin = totRecords  %  pageSize ;
        //총 페이지수 구하기
        if( reamin ==0 )
            totalPage = totRecords / pageSize;
        else
            totalPage = totRecords / pageSize +1;
        // 현재그룹 구하기
        // 현재그룹의 시작번호
        // 현재그룹의 끝번호
        int remain2 = currentPage % grpSize;

        if( remain2 ==0 )
            currentGrp  = currentPage  / grpSize ;
        else
            currentGrp = currentPage  / grpSize  +1;




        grpStartPage = ( currentGrp -1 ) * grpSize +1 ;  // 그룹의 시작번호   현재그룹 1 => 1  , 현재그룹 2 -> 6
        grpEndPage = currentGrp * grpSize;               // 그룹의 끝번호    1-> 5  , 2-> 10


        // 그룹의 마지막번호가 마지막페이지보다 크다면
        //그룹의 마지막번호가 마지막페이지로 변경해야됨
        if( grpEndPage > totalPage){
            grpEndPage = totalPage;                      // 그룹의 끝번호   10 -> 9로 변경 , 그룹의 끝번호가 마지막페이지번호 크다면
        }




    }

}
