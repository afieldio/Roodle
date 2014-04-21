package io.afield.roodle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;

public class JsonClient {
	
	private String baseUrl = "http://localhost:8888/";
	private static JsonClient instance;
	
	private JsonClient(){
		
	}
	
	public static synchronized JsonClient getInstance(){
		if(instance == null){
			instance = new JsonClient();
		}
		
		return instance;
	}
	
	public <T> void sendPost(Object requestObject, String uri, final ResponseCallback<Score> callback, final Class<Score> class1) {
       
		String GET = new String();
		GET = "GET";
		
		sendRequest(requestObject, uri, GET, callback, class1);
    }
    private <T> void sendRequest(Object requestObject, String uri, String method, final ResponseCallback<T> callback, final Class<T> clazz) {

        String requestJson = JsonUtil.getInstance().toJson(requestObject);

        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = getURL(uri);
        request.setUrl(url);

        request.setContent(requestJson);

        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
       
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

                int statusCode = httpResponse.getStatus().getStatusCode();
                if(statusCode != HttpStatus.SC_OK) {
                    callback.onFail(new JsonClientException(url,"Received http status: " + statusCode));
                    return;
                }

                String responseJson = httpResponse.getResultAsString();
                try {
                    T responseObject = JsonUtil.getInstance().fromJson(clazz, responseJson);
                    callback.onResponse(responseObject);
                }
                catch(Exception exception) {
                    callback.onFail(new JsonClientException(url, "Exception parsing response as JSON.", exception));
                }
            }

            public void failed(Throwable t) {
                callback.onFail(new JsonClientException(url, t));
            }

			@Override
			public void cancelled() {
				// TODO Auto-generated method stub
				
			}
        });

    }

    private String getURL(String uri) {
        return baseUrl + uri;
    }
    
    public interface ResponseCallback<T> {

	    void onResponse(T returnObject);
	    void onFail(JsonClientException t);

	}

}