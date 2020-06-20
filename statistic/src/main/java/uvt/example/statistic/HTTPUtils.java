package uvt.example.statistic;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class HTTPUtils {
	private String doiX;
	private String doiY;
	private InformationType informationType;

	public HTTPUtils(String doiX, String doiY, InformationType informationType) {
		this.doiX = doiX;
		this.doiY = doiY;
		this.informationType = informationType;
	}

	public String getInformation() {
		HttpGet request = new HttpGet(informationType.getUrl() + doiX + "/" + doiY);

		// add request headers
		request.addHeader("Accept", "application/citeproc+json");
		request.addHeader("Accept", "application/unixref+xml");

		String result = null;
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
		     CloseableHttpResponse response = httpClient.execute(request)) {

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = response.getEntity();
				if (httpEntity != null) {
					result = EntityUtils.toString(httpEntity);
				} else {
					return "";
				}
			} else {
				return "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	enum InformationType {
		PLU("https://plu.mx/api/v1/artifact/doi/"),
		DOI("http://dx.doi.org/"),
		WOS("http://ws.isiknowledge.com/cps/openurl/service?url_ver=Z39.88-2004&rft_id=info:doi/");

		private String url;

		private InformationType(String url) {
			this.url = url;
		}

		public String getUrl() {
			return this.url;
		}
	}


}
