/**
 * 
 */
package com.pivotal.cf.broker.service.oracle;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pivotal.cf.broker.exception.ServiceBrokerException;
import com.pivotal.cf.broker.exception.ServiceInstanceBindingExistsException;
import com.pivotal.cf.broker.model.ServiceInstance;
import com.pivotal.cf.broker.model.ServiceInstanceBinding;
//import com.pivotal.cf.broker.repos.OracleDBServiceBindingRepository;
import com.pivotal.cf.broker.service.ServiceInstanceBindingService;
import com.pivotal.cf.broker.util.OracleDBManager;

/**
 * @author opstack
 * 
 */
@Service
public class OracleDBServiceInstanceBindingService implements
		ServiceInstanceBindingService {

	@Autowired
	private OracleDBManager dbManager;
	private Map<String, ServiceInstanceBinding> bindingRepo = new HashMap<String, ServiceInstanceBinding>();

	/*
	 * @Autowired public OracleDBServiceInstanceBindingService(){
	 * 
	 * }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pivotal.cf.broker.service.ServiceInstanceBindingService#
	 * createServiceInstanceBinding(java.lang.String,
	 * com.pivotal.cf.broker.model.ServiceInstance, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceInstanceBinding createServiceInstanceBinding(
			String bindingId, ServiceInstance serviceInstance,
			String serviceId, String planId, String appGuid)
			throws ServiceInstanceBindingExistsException,
			ServiceBrokerException {
		// TODO Auto-generated method stub
		ServiceInstanceBinding binding = bindingRepo.get(bindingId);
		if (binding != null) {
			throw new ServiceInstanceBindingExistsException(binding);
		}

		String database = serviceInstance.getId();
		String username = bindingId;
		// TODO Password Generator
		String password = "password";

		// TODO check if user already exists in the DB
		Map<String, String> connArr = dbManager.createUser(username, password,
				database);
		if (connArr.isEmpty())
			throw new ServiceBrokerException(
					"Failed to create new service binding: " + bindingId);
		// mongo.createUser(database, username, password);
		String uri = connArr.get("driver") + ":@" + connArr.get("host") + ":"
				+ connArr.get("port") + "/" + database;
		Map<String, Object> credentials = new HashMap<String, Object>();
		credentials.put("uri", uri);
		credentials.put("driver", connArr.get("driver"));
		credentials.put("host", connArr.get("host"));
		credentials.put("port", connArr.get("port"));
		credentials.put("database", database);
		credentials.put("username", username);
		credentials.put("password", password);

		binding = new ServiceInstanceBinding(bindingId,
				serviceInstance.getId(), credentials, null, appGuid);
		// repository.save(binding);
		bindingRepo.put(bindingId, binding);

		return binding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pivotal.cf.broker.service.ServiceInstanceBindingService#
	 * getServiceInstanceBinding(java.lang.String)
	 */
	@Override
	public ServiceInstanceBinding getServiceInstanceBinding(String id) {
		return bindingRepo.get(id);
		// return bindingRepo.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pivotal.cf.broker.service.ServiceInstanceBindingService#
	 * deleteServiceInstanceBinding(java.lang.String)
	 */
	@Override
	public ServiceInstanceBinding deleteServiceInstanceBinding(String id)
			throws ServiceBrokerException {
		/*
		 * ServiceInstanceBinding serviceBinding = bindingRepo.findOne(id); if
		 * (serviceBinding != null) {
		 * //mongoDBAdminService.deleteUser(serviceInstanceBinding
		 * .getServiceInstanceId(), id); bindingRepo.delete(id); }
		 */
		dbManager.deleteUser(id);
		ServiceInstanceBinding serviceBinding = bindingRepo.remove(id);
		return serviceBinding;
	}

}
