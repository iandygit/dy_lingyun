package com.business.pound.service.impl;


import com.business.pound.entity.PoundEntity;
import com.business.pound.entity.TransportEnetity;
import com.business.pound.repository.PoundRepository;
import com.business.pound.repository.TransportRepository;
import com.business.pound.service.TransportService;
import com.business.pound.util.OrderCodeFactory;
import com.business.pound.util.PoundEnum;
import com.business.pound.util.TransportEnum;
import com.business.pound.vo.PoundTransVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import com.lingyun.core.exception.ValidateCodeException;
@Service
public class TransportServiceImpl implements TransportService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransportRepository transportRepository;
    @Autowired
    private PoundRepository poundRepository;

    @Override
    public TransportEnetity save(TransportEnetity transportEnetity) {
        return transportRepository.saveAndFlush(transportEnetity);
    }

    @Override
    public void delete(TransportEnetity transportEnetity) {
        transportRepository.delete(transportEnetity);
    }

    @Override
    public List<TransportEnetity> getAll() {
        return transportRepository.findAll();
    }

    @Override
    public Page<TransportEnetity> getPage(Example<TransportEnetity> example, Pageable pageable) {

        return transportRepository.findAll(example,pageable);
    }


    public TransportEnetity getOne(Long id) {

        Optional<TransportEnetity> transportEnetity=transportRepository.findById(id);

        return transportEnetity.get();
    }

    @Override
    public void deleteById(Long id) {
        transportRepository.deleteById(id);
    }

    @Override
    public Page<PoundTransVo> getPageTransport(String transportNum, Pageable pageable) {

        if(StringUtils.isEmpty(transportNum)){

            return transportRepository.findAllTransport(pageable);
        }
        return transportRepository.findAllTransport(transportNum,pageable);
    }

    @Override
    public List<PoundTransVo> findAllList(String transportNum) {

         if(org.apache.commons.lang3.StringUtils.isEmpty(transportNum)){
             transportRepository.findAllList();
         }
        return transportRepository.findAllList(transportNum);
    }

    @Override
    @Transactional
    public int apporval(String[] ids, String status) {
        String cartNum="";
        TransportEnetity transportEnetity=new TransportEnetity();
        String transNum=OrderCodeFactory.getTransCode(1L);
        double  weight=0.0;//毛重
        double  tareWeight=0.0;//皮重
        double  netWeight=0.0;//净重

         for(int i=0;i<ids.length;i++){

             Long idV=Long.valueOf(ids[i]);
             //transportEnetity.setPoundId(idV);

             PoundEntity poundEntity=poundRepository.getOne(idV);

             if(null==poundEntity ||null== poundEntity.getId()){//找不到数据

                 continue;
             }
             if(StringUtils.isEmpty(cartNum)){
                 cartNum=poundEntity.getCarNum();
             }else {
                 if(!cartNum.equals(poundEntity.getCarNum())){//不是同一个车的审批，放弃

                     throw new ValidateCodeException("车号不同，请检查数据合法性");
                     //continue;
                 }
             }
             poundEntity.setPoundStatus(PoundEnum.valueOf(status));//审批结果
             poundEntity.setTransportNum(transNum);

             transportEnetity.setGoodsName(poundEntity.getGoodsName());//货物名称
             transportEnetity.setReciveUnit(poundEntity.getReciveUnit());//收货单位
             transportEnetity.setDeliverUnit(poundEntity.getDeliverUnit());//发货单位
             transportEnetity.setCarNum(poundEntity.getCarNum());//汽车号
             if(ids.length==1){

                 transportEnetity.setWeight(poundEntity.getWeight());//毛重
                 transportEnetity.setTareWeight(poundEntity.getTareWeight());//皮重
                 transportEnetity.setNetWeight(poundEntity.getWeight()-poundEntity.getTareWeight());//净重
             }else {
                  if(null!=poundEntity.getWeight()){
                      weight=poundEntity.getWeight();
                  }
                   if(null != poundEntity.getTareWeight()){
                       tareWeight=poundEntity.getTareWeight();
                   }
                  if(i>0){//第二条数据
                      /******
                       * 如果两个磅单分别提供了毛重和皮重，则分别存入运单的毛重和皮重字段，
                       * 并且用毛重-皮重得出净重计入运单；
                       * 如果两个磅单都提供了毛重字段[或其他重量字段]，
                       * 那么，用这两个数量做比较，大的作为毛重存入运单，
                       * 小的作为皮重存入运单，用上面的公式计算净重存入运单*
                       * *********/
                      if(weight>poundEntity.getWeight()){
                          transportEnetity.setWeight(weight);//毛重
                          transportEnetity.setTareWeight(poundEntity.getWeight());//皮重
                          transportEnetity.setNetWeight(weight-poundEntity.getWeight());
                      }else if (weight==poundEntity.getWeight()){
                          transportEnetity.setWeight(poundEntity.getWeight());//毛重
                          transportEnetity.setTareWeight(poundEntity.getTareWeight());//皮重
                          transportEnetity.setNetWeight(poundEntity.getWeight()-poundEntity.getTareWeight());//净重

                      }else{
                          transportEnetity.setWeight(poundEntity.getWeight());//毛重
                          transportEnetity.setWeight(weight);//皮重
                          transportEnetity.setNetWeight(poundEntity.getWeight()-weight);//净重
                      }

                  }
             }
             transportEnetity.setPoundAccount(poundEntity.getPoundAccount());
        }
        //transportEnetity.setPoundNum(poundEntity.getPoundNum());


        transportEnetity.setStatus(TransportEnum.A);

        transportEnetity.setTransportNum(transNum);

        transportRepository.save(transportEnetity);
        return 1;
    }

    @Override
    public Page<TransportEnetity> findAll(String transportNum, Pageable pageable) {
         if(StringUtils.isEmpty(transportNum)){
             return transportRepository.findAll(pageable);
         }
        return transportRepository.findAllByTransportNum(transportNum,pageable);

    }
}
