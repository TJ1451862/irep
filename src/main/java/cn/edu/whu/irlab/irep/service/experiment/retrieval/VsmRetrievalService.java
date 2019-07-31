package cn.edu.whu.irlab.irep.service.experiment.retrieval;

import cn.edu.whu.irlab.irep.service.vo.Query;
import cn.edu.whu.irlab.irep.service.vo.VectorIVo;
import cn.edu.whu.irlab.irep.service.vo.IdfVo;
import cn.edu.whu.irlab.irep.service.vo.ResultVo;
import cn.edu.whu.irlab.irep.service.vo.TfVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author gcr
 * 向量空间模型检索接口
 */
public interface VsmRetrievalService extends RetrieverService {

    /**
     * 初始化向量空间检索器
     * @param queryContent 查询语句内容
     * @param formulaID 公式Id
     * @param smoothParam 平滑系数
     * @param request HttpServletRequest
     */
    void initVSMRetriever(String queryContent, int formulaID, double smoothParam, HttpServletRequest request);

    /**
     *计算idf
     * @return idf
     */
    List<IdfVo> calculateIdf();

    /**
     * 计算查询的TF
     * @return 查询的TF
     */
    List<TfVo> calculateTfOfQuery();

    /**
     * 计算文档的TF
     * @param docId 文档Id
     * @return 文档的TF
     */
    List<TfVo> calculateTfOfDoc(int docId);

    /**
     * 计算查询的向量
     * @return 查询的向量
     */
    List<VectorIVo> calculateVectorOfQuery();

    /**
     * 计算文档的向量
     * @param docId 文档Id
     * @return 文档向量
     */
    List<VectorIVo> calculateVectorOfDoc(int docId);

    /**
     * 计算相似度
     * @return 相似度
     */
    List<ResultVo> calculateSimilarity();

    /**
     * 按相似度降序排序
     * @return 降序排序后的相似度
     */
    List<ResultVo> descendOrderSimilarity();

    /**
     * 返回查询对象
     * @return 查询对象
     */
    Query getQuery();
}

