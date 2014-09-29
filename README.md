Noah LOL API Library v1.0.0
=======================

�ȵ���̵� ���� ���׿��극���� API ���̺귯�� �Դϴ�.

Async(�񵿱�), Sync(����) ������� �����͸� ȹ�� �� �� �ֽ��ϴ�.


�� ���� ��� ���� �ݵ�� Thread ���� ó�����ּ���.

�����
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

�ֿ� Class �Ұ�
---------
```
���� ������Ʈ ����.
```


������Ʈ ����
---------
[2014-09-29]
��ȯ�� ���� ��������.


License
---------
Apache License 2.0