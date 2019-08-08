package cn.edu.whu.irlab.irep.controller.experiment;

import cn.edu.whu.irlab.irep.service.experiment.simulation.Impl.SimulationServiceImpl;
import cn.edu.whu.irlab.irep.service.experiment.simulation.SimulationService;
import cn.edu.whu.irlab.irep.service.vo.SearchResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gcr19
 * @date 2019-08-08 08:09
 * @desc
 **/
@RestController
@RequestMapping("IRforCN/Simulation")
public class SimulationController {


    @Autowired
    private SimulationService simulationService;

    @PostMapping("/selectModel")
    public Map<String,String> selectModel(@RequestParam("modelName") String modelName, HttpServletRequest request){
        Map<String,String> state=new HashMap<>();
        int code=simulationService.selectModel(modelName,request);
        state.put("code",String.valueOf(code));
        if (code==1){
            state.put("message","成功！");
        }else {
            state.put("message","失败。");
        }
        return state;
    }

    @PostMapping("/search")
    public List<SearchResultVo> search(@RequestParam("query") String query,HttpServletRequest request){
        return simulationService.search(query,request);
    }
}
