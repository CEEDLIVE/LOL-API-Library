Noah LOL API Library v1.0.1
=======================

안드로이드 버전 리그오브레전드 API 라이브러리 입니다.

Async(비동기), Sync(동기) 기반으로 데이터를 획득 할 수 있습니다.

(지속적 업데이트를 통해 전적, 룬 등 다양한 기능을 제공할 예정)



▶ 동기 기반 사용시 반드시 Thread 에서 처리해주세요.



사용방법
---------
https://developer.riotgames.com 홈페이지에서 한국서버 로그인 후

(실제 게임서버) API KEY 를 부여받고, 본 라이브러리를 사용합니다.


```
EnvironmentConfig.getInstance().initialize("API_KEY", Region.KR);

Summoner summoner = new Summoner();

//Async
summoner.getAsyncSummonerInfo(name, new SimpleNetworkListener<SummonerDto>() {
		        	
	@Override
	public void onSuccess(SummonerDto data) {
		//Success        		
	}
		        	
	@Override
	public void onNetworkFail(int errorCode, NetworkException e) {
		//HTTP Error	
	}
		        	
});

//Sync (반드시 스레드 에서 처리)
try {
	SummonerDto dto = summoner.getSyncSummonerInfo("게임닉네임");
} catch (NullPointerException e) {   
	e.printStackTrace();							
} catch (NetworkException e1) {    
	e1.printStackTrace();
}
```



주요 Class 소개
---------
추후 업데이트 예정.



업데이트 내역
---------
[2014-10-04] 

소환사 룬페이지 정보 가져오기 

[2014-09-29] 

소환사 정보 가져오기.


License
---------
Apache License 2.0