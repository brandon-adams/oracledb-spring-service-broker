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

/**
 * @author opstack
 *
 */
@Service
public class OracleDBServiceInstanceBindingService implements
		ServiceInstanceBindingService {

	/*@Autowired
	OracleDBServiceBindingRepository bindingRepo;*/
	Map<String, ServiceInstanceBinding> bindingRepo = new HashMap<String, ServiceInstanceBinding>();
	
	/*@Autowired
	public OracleDBServiceInstanceBindingService(){
		
	}*/
	
	/* (non-Javadoc)
	 * @see com.pivotal.cf.broker.service.ServiceInstanceBindingService#createServiceInstanceBinding(java.lang.String, com.pivotal.cf.broker.model.ServiceInstance, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceInstanceBinding createServiceInstanceBinding(
			String bindingId, ServiceInstance serviceInstance,
			String serviceId, String planId, String appGuid)
			throws ServiceInstanceBindingExistsException,
			ServiceBrokerException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.pivotal.cf.broker.service.ServiceInstanceBindingService#getServiceInstanceBinding(java.lang.String)
	 */
	@Override
	public ServiceInstanceBinding getServiceInstanceBinding(String id) {
		return bindingRepo.get(id);
		//return bindingRepo.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.pivotal.cf.broker.service.ServiceInstanceBindingService#deleteServiceInstanceBinding(java.lang.String)
	 */
	@Override
	public ServiceInstanceBinding deleteServiceInstanceBinding(String id)
			throws ServiceBrokerException {
		/*ServiceInstanceBinding serviceBinding = bindingRepo.findOne(id);
		if (serviceBinding != null) {
			//mongoDBAdminService.deleteUser(serviceInstanceBinding.getServiceInstanceId(), id);
			bindingRepo.delete(id);
		}*/
		ServiceInstanceBinding serviceBinding = bindingRepo.remove(id);
		return serviceBinding;
	}

}
