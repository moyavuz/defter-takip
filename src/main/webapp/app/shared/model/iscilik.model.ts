import { IEkip } from 'app/shared/model/ekip.model';
import { IPoz } from 'app/shared/model/poz.model';
import { IProje } from 'app/shared/model/proje.model';

export interface IIscilik {
    id?: number;
    miktar?: number;
    aciklama?: any;
    resimContentType?: string;
    resim?: any;
    ekip?: IEkip;
    poz?: IPoz;
    proje?: IProje;
}

export class Iscilik implements IIscilik {
    constructor(
        public id?: number,
        public miktar?: number,
        public aciklama?: any,
        public resimContentType?: string,
        public resim?: any,
        public ekip?: IEkip,
        public poz?: IPoz,
        public proje?: IProje
    ) {}
}
