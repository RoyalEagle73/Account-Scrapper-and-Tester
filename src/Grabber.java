import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

class Grabber {
	
	Set<String> temp_url_list;
	Set<String> final_url_list;
	Set<String> data_to_fetch;
	
	Grabber(Set<String> data){
		temp_url_list = new HashSet<String>();
		final_url_list = new HashSet<String>();
		data_to_fetch = new HashSet<String>();
		this.data_to_fetch = data;
	}
	
	Set<String> getFinalUrls(JLabel label){
			for(String data : data_to_fetch) {
				url_leech leech_object = new url_leech(data);
				temp_url_list = leech_object.ret_url(label);
				for(String url:temp_url_list) {
					try {
						final_url_list.add(url);	
						} catch (Exception e) {
					}
				}
			}
		return final_url_list;
	}
	
}