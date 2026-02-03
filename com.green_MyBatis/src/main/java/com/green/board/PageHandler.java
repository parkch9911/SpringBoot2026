package com.green.board;

// 페이징을 하기 위한 계산식을 가지고 있는 클래스
public class PageHandler {
	
	//1. 기본 변수
	private int totalCnt; //전체 게시글 게수
	private int pageNum; //현재 페이지 번호
	private int pageSize; //한 페이지에 보여줄 레코드(게시물) 개수
	private int pageBlock = 3; //한 화면에 페이지묶음 1~3페이지까지
	
	//2. DB 조회 변수
	// Limit 1(startRow),5(pageSize) => 1부터 시작해서 5개만 출력 
	private int startRow; //DB의 시작 위치
	private int endRow; //가져올 게시글의 개수 = pageSize (Limit이 없으면 계산해야해서 확장성 때문에 남겨둔다고하심
	
	//3. pageBlock 부분 : [1][2][3], [4][5][6]
	private int totalPage; //전체 페이지 수
	private int startPage; //블록페이지의 시작번호 
	private int endPage;   //블록페이지의 마지막번호
	
	private boolean prev; //◀ 이전 
	private boolean next; //▶ 다음
	
	//생성자
	public PageHandler(int totalCnt, int pageNum, int pageSize) {
		this.totalCnt = totalCnt;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		
		//계산 함수 콜 예정
		calcPaging();
	}
	
	//페이지 계산하는 메소드
	public void calcPaging() {
		//totalPage 전체 페이지 수
		//[1][2][3], [4][5][6]
		//게시글의 개수 증감
		//Math.ceil() => 소수점을 반올림하여 정수로 출력하는 메소드
		totalPage = (int) Math.ceil(totalCnt/(double)pageSize);
		
		//DB에서 조회하는 범위 , 첫 번째 
		//1페이지 => 0부터 5개
		//2페이지 => 5부터 5개
		//pageNum = 1 => 현재 페이지 번호
		//0 ~~   5 ~~   10 ~~
		//4 ~~   9 ~~   14 ~~
		//[1]    [2]    [3]
		startRow = (pageNum-1)*pageSize;
		endRow = pageSize;
		
		// pageBlock = 3 블록페이지의 시작 / 끝 
		//[1][2][3], [4][5][6]
		// pageNum이 1 또는 2또는 3이어이도 startPage 는 1이어야한다.
		startPage = ((pageNum - 1) / pageBlock)*pageBlock+1;
		endPage = startPage + (pageBlock - 1);
		
		//실제 페이지수가 3의배수가 아니면 마지막페이지를 강제로 endPage에 담아준다
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		
		// 이전 / 다음 버튼 여부
		prev = startPage > 1;
		next = endPage < totalPage;
		
		
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageBlock() {
		return pageBlock;
	}

	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}
	
	
	
	
	
}
