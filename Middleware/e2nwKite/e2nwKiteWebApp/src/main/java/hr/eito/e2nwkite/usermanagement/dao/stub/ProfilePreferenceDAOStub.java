
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


package hr.eito.e2nwkite.usermanagement.dao.stub;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import hr.eito.e2nwkite.usermanagement.dao.ProfilePreferenceDAO;
import hr.eito.e2nwkite.usermanagement.model.ProfilePreference;

@Repository
@Profile({"test"})
public class ProfilePreferenceDAOStub implements ProfilePreferenceDAO {
	
	private List<ProfilePreference> repository;
	
	@PostConstruct
	public void init() {
		repository = new ArrayList<>();
		
		ProfilePreference ppr1 = new ProfilePreference();
		ppr1.setId(1);
		ppr1.setUser(null);
		ppr1.setUserGroup(null);
		
		ProfilePreference ppr2 = new ProfilePreference();
		ppr2.setId(2);
		ppr2.setUser(null);
		ppr2.setUserGroup(null);
		
		repository.add(ppr1);
		repository.add(ppr2);
	}

	@Override
	public ProfilePreference getGlobal() {
		for(ProfilePreference ppr : repository) {
			if(ppr.getUser()==null && ppr.getUserGroup()==null) {
				return ppr;
			}
		}
		return null;
	}

	@Override
	public ProfilePreference getById(Integer id) {
		for(ProfilePreference ppr : repository) {
			if(ppr.getId().equals(id)) {
				return ppr;
			}
		}
		return null;
	}
	
}
