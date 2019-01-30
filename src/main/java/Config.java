import java.io.*;

public class Config {
	private String token;
	private String prefix = ">";
	private long ownerId;
	
	public Config() throws Exception {
		File file = new File("data/config.txt"); 
		BufferedReader text = new BufferedReader(new FileReader(file)); 
	  
		String st;
		String [] parameters;
		while ((st = text.readLine()) != null) {
			if (!(st.startsWith("//") || st.isEmpty())) {
				parameters = st.split("=", 2);
				
				if (parameters[0].equals("token")) {
					if (parameters[1] == "ENTER_TOKEN_HERE") {
						System.out.println("Replace ENTER_TOKEN_HERE with your bot's token value in data/config.txt");
					}
					this.token = parameters[1];
				}
				else if (parameters[0].equals("owner")) {
					if (parameters[1] == "ENTER_OWNER_ID_HERE") {
						System.out.println("Replace ENTER_OWNER_ID_HERE with your owner ID in data/config.txt");
					}
					this.ownerId = Long.parseLong(parameters[1]);
				}
				else if (parameters[0].equals("prefix")) {
					this.prefix = parameters[1];
				}
			}
		}
		
		text.close();
	}
	
	public String getToken() {
		return this.token;
	}
	
	public long getOwnerId() {
		return this.ownerId;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
}