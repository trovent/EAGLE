
/*
    Copyright (C) 2017 e-ito Technology Services GmbH
    e-mail: info@e-ito.de
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/


package hr.eito.model.lara.rules.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Lara Rules hosts record
 * 
 * @author Hrvoje
 *
 */
public class HostsRecord {
	
	@JsonProperty("no")
	private int rank;
	@JsonProperty("ip")
	private String ipAddress;
	@JsonProperty("count")
	private long hits;
	@JsonProperty("size")
	private long size;
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public long getHits() {
		return hits;
	}
	public void setHits(long hits) {
		this.hits = hits;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}	
	
}
