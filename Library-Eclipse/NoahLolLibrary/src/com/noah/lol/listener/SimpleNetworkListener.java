/*
    Android Noah LOL API
    Copyright (c) 2014 Noah Hahm <dbgtdbz2@naver.com>
    http://globalbiz.tistory.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.noah.lol.listener;

import com.noah.lol.exception.NetworkException;

public class SimpleNetworkListener<T> implements NetworkListener<T> {

	@Override
	public void onSuccess(T result) {
		
	}

	@Override
	public void onNetworkFail(int errorCode, NetworkException e) {
		
	}
		
}
