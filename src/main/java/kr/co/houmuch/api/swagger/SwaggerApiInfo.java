package kr.co.houmuch.api.swagger;

public class SwaggerApiInfo {
    // controller 대제목(단어)
    public static final String CONTRACT = "CONTRACT 관련 API";

    public static final String GET_PROCESS_INDEX = "type별 지역 거래(지역/총거래량/평균거래량 리스트) 캐싱";
    // 각 controller 설명(받아오는방식_어떤걸_받아오는지)
    public static final String GET_AREA_CONTRACT_LIST = "type별 지역 거래(지역/총거래량/평균거래량 리스트)";
    public static final String SEARCH = "SEARCH 관련 API";
    public static final String GET_SEARCH_LIST = "연관 검색어 리스트";
    public static final String HOSPITAL = "HOSPITAL 관련 API";
    public static final String GET_HOSPITAL_LIST = "좌표값에 따른 병원 리스트";
    public static final String STORE = "STORE 관련 API";
    //public static final String ?? ="상점 정보 캐싱"
    public static final String GET_STORE_LIST = "좌표값에 따른 상점 리스트";

}
