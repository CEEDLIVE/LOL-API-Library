Noah LOL API Library v1.0.1
=======================

�ȵ���̵� ���� ���׿��극���� API ���̺귯�� �Դϴ�.

Async(�񵿱�), Sync(����) ������� �����͸� ȹ�� �� �� �ֽ��ϴ�.

(������ ������Ʈ�� ���� ����, �� �� �پ��� ����� ������ ����)



�� ���� ��� ���� �ݵ�� Thread ���� ó�����ּ���.



�����
---------
https://developer.riotgames.com Ȩ���������� �ѱ����� �α��� ��

(���� ���Ӽ���) API KEY �� �ο��ް�, �� ���̺귯���� ����մϴ�.


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

//Sync (�ݵ�� ������ ���� ó��)
try {
	SummonerDto dto = summoner.getSyncSummonerInfo("���Ӵг���");
} catch (NullPointerException e) {   
	e.printStackTrace();							
} catch (NetworkException e1) {    
	e1.printStackTrace();
}
```



�ֿ� Class �Ұ�
---------
���� ������Ʈ ����.



������Ʈ ����
---------
[2014-10-04] 

��ȯ�� �������� ���� �������� 

[2014-09-29] 

��ȯ�� ���� ��������.


License
---------
Apache License 2.0