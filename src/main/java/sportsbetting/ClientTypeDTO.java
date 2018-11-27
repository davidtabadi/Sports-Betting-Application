package sportsbetting;

import java.io.Serializable;

public class ClientTypeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5464258309163360083L;

	private String name;
	private String password;
	private ClientType clientType;

	public ClientTypeDTO() {
		super();
	}

	public ClientTypeDTO(String name, String password, ClientType clientType) {
		super();
		this.name = name;
		this.password = password;
		this.clientType = clientType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	@Override
	public String toString() {
		return "ClientTypeDTO [name=" + name + ", password=" + password + ", clientType=" + clientType + "]";
	}

}
