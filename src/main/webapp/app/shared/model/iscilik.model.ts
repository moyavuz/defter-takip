import { IPoz } from 'app/shared/model/poz.model';
import { IEkip } from 'app/shared/model/ekip.model';
import { IProje } from 'app/shared/model/proje.model';

export interface IIscilik {
    id?: number;
    miktar?: number;
    aciklama?: any;
    resimContentType?: string;
    resim?: any;
    poz?: IPoz;
    calisanEkip?: IEkip;
    proje?: IProje;
}

export class Iscilik implements IIscilik {
    constructor(
        public id?: number,
        public miktar?: number,
        public aciklama?: any,
        public resimContentType?: string,
        public resim?: any,
        public poz?: IPoz,
        public calisanEkip?: IEkip,
        public proje?: IProje
    ) {}
}
