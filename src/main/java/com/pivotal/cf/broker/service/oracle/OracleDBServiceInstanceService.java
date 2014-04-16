/**
 * 
 */
package com.pivotal.cf.broker.service.oracle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pivotal.cf.broker.exception.ServiceBrokerException;
import com.pivotal.cf.broker.exception.ServiceInstanceExistsException;
import com.pivotal.cf.broker.model.ServiceDefinition;
import com.pivotal.cf.broker.model.ServiceInstance;
//import com.pivotal.cf.broker.repos.OracleDBServiceRepository;
import com.pivotal.cf.broker.service.ServiceInstanceService;

/**
 * @author opstack
 *
 */
@Service
public class OracleDBServiceInstanceService implements ServiceInstanceService {

	//private Map<String, ServiceInstance> serviceInsts = new HashMap<String, ServiceInstance>();
	//@Autowired
	//OracleDBServiceRepository serviceRepo;
	Map<String, ServiceInstance> serviceRepo = new HashMap<String, ServiceInstance>();
	/*@Autowired
	public OracleDBServiceInstanceService(){
		
	}*/
	
	/* (non-Javadoc)
	 * @see com.pivotal.cf.broker.service.ServiceInstanceService#getAllServiceInstances()
	 */
	@Override
	public List<ServiceInstance> getAllServiceInstances() {
		List<ServiceInstance> serviceInstances = new ArrayList<ServiceInstance>();
	    /*for (ServiceInstance serviceInstance : serviceRepo.findAll()) {
	    	serviceInstances.add(serviceInstance);
	    }*/
		Iterator<ServiceInstance> itr = serviceRepo.values().iterator();
		
		while( itr.hasNext() ){
			serviceInstances.add(itr.next());
		}
	    
	    return serviceInstances;
	}

	/* (non-Javadoc)
	 * @see com.pivotal.cf.broker.service.ServiceInstanceService#createServiceInstance(com.pivotal.cf.broker.model.ServiceDefinition, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceInstance createServiceInstance(ServiceDefinition service,
			String serviceInstanceId, String planId, String organizationGuid,
			String spaceGuid) throws ServiceInstanceExistsException,
			ServiceBrokerException {
		
		/*ServiceInstance instance = serviceRepo.findOne(serviceInstanceId);
		if (instance != null) {
			throw new ServiceInstanceExistsException(instance);
		}*/

		ServiceInstance instance = serviceRepo.get(serviceInstanceId);
		if (instance != null) {
			throw new ServiceInstanceExistsException(instance);
		}
		instance = new ServiceInstance(serviceInstanceId, service.getId(),
				planId, organizationGuid, spaceGuid, null);

		/*if (mongoDBAdminService.databaseExists(instance.getId())) {
			mongoDBAdminService.deleteDatabase(instance.getId());
		}*/

		//At this point, I have to execute a SQL statement to create the table in the database
		
		/*DB db = mongoDBAdminService.createDatabase(instance.getId());
		if (db == null) {
			throw new ServiceBrokerException("Failed to create new DB instance: " + instance.getId());
		}*/
		//serviceRepo.save(instance);
		serviceRepo.put(serviceInstanceId, instance);
		return instance;
	}

	/* (non-Javadoc)
	 * @see com.pivotal.cf.broker.service.ServiceInstanceService#getServiceInstance(java.lang.String)
	 */
	@Override
	public ServiceInstance getServiceInstance(String id) {
		return serviceRepo.get(id);
		//return serviceRepo.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.pivotal.cf.broker.service.ServiceInstanceService#deleteServiceInstance(java.lang.String)
	 */
	@Override
	public ServiceInstance deleteServiceInstance(String id)
			throws ServiceBrokerException {
		/*ServiceInstance instance = serviceRepo.findOne(id);
		serviceRepo.delete(id);*/
		ServiceInstance instance = serviceRepo.remove(id);
		return instance;
	}

}
