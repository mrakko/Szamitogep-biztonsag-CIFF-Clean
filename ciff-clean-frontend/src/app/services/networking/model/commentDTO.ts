/**
 * Ciff Clean - OpenAPI 3.0
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 0.9.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { PublicUserDTO } from './publicUserDTO';

export interface CommentDTO { 
    id: number;
    text: string;
    commenter: PublicUserDTO;
}