package novel.web.entity;

public class JsonResponse {
	public int status;
	public String desc = "";
	public Object data;

	public static JsonResponse success(Object data) {
		JsonResponse response = new JsonResponse();
		response.setStatus(1);
		response.setData(data);
		return response;
	}

	public static JsonResponse error(String desc) {
		JsonResponse response = new JsonResponse();
		response.setStatus(0);
		response.setDesc(desc);
		return response;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
