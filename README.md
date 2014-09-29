Noah LOL API Library v1.0.0
=======================

안드로이드 버전 리그오브레전드 API 라이브러리 입니다.

Async(비동기), Sync(동기) 기반으로 데이터를 획득 할 수 있습니다.


▶ 동기 기반 사용시 반드시 Thread 에서 처리해주세요.

사용방법
---------
```
EnvironmentConfig.getInstance().initialize("API_KEY");

Summoner summoner = new Summoner();

//Async
summoner.getAsyncSummonerInfo(name, Region.KR, new 
SimpleNetworkListener<SummonerDto>() {
		        	
	@Override
	public void onSuccess(SummonerDto data) {
		//Success
	}
		        	
	@Override
	public void onNetworkFail(int errorCode, NetworkException e) {
		//HTTP Error		        		
	}
		        	
});

//Sync
try {
	SummonerDto data = summoner.getSyncSummonerInfo("NickName", Region.KR);
} catch (NetworkException e) {
	e.printStackTrace();
}
```

주요 Class 소개
---------
```
추후 업데이트 예정.
```


업데이트 내역
---------
[2014-09-29]
소환사 정보 가져오기.


License
---------
Apache License 2.0